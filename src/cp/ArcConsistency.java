package cp;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

/**
 * Cette classe permet de définir une arc coherence de noeuds à fin de rendre un csp coherent
 * On utilise cette classe pour réduire les domaines de variables dans un csp en eliminant les valeurs qui ne satisfont pas les constraintes
 * garentir que le csp est coherent
 */

public class ArcConsistency {

   Set<Constraint> unaryConstraints; //contraintes unaires
   Set<Constraint> binaryConstraints; //Contraintes binaires    


   public ArcConsistency(Set<Constraint> ensembleConstraints) throws IllegalArgumentException{
     init(ensembleConstraints);
   }

   private void init(Set<Constraint> constraints){
    unaryConstraints = new HashSet<>();
    binaryConstraints = new HashSet<>();

    for(Constraint constraint: constraints){
        Set<Variable> scope = constraint.getScope();

        if(scope.size() == 1){
            unaryConstraints.add(constraint);
        }else if (scope.size() == 2){
            binaryConstraints.add(constraint);
        }
        else{
            throw new IllegalArgumentException("ni unaire et ni binaire");
        }
    }
   }

   /**
    *  L'algorithme de Consistance des Noeuds
    *
    * @param domaineEvolutionMap l'ensemble de domaine à traite
    * @return boolean et supprime toutes les valeurs de variables qui ne sont pas cohérentes
    */
    
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domaineEvolutionMap) {
        for (Variable var : domaineEvolutionMap.keySet()) {
            Set<Object> valDomain = domaineEvolutionMap.get(var);
            Set<Object> deleteValeur = new HashSet<>();
            for (Object val : valDomain) {
    
                for (Constraint constraint : unaryConstraints) {
                    if (constraint.getScope().contains(var)) {
                        Map<Variable, Object> state = new HashMap<>();
                        state.put(var, val);
                        if (!constraint.isSatisfiedBy(state)) {
                            deleteValeur.add(val);
                        }
                    }
                }
            }
            valDomain.removeAll(deleteValeur);
        }
    
        for (Variable var : domaineEvolutionMap.keySet()) {
            if (domaineEvolutionMap.get(var).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Implementation de l'algorithme Revise
     * @param variableUn
     * @param domaineUn
     * @param variableDeux
     * @param domaineDeux
     * @return Un booléen qui indique si les valeurOne qui n'ont pas de support ,ils ont été supprimées des domaines de variableUn si true,sinon le domaine reste inchangé
     */

     public boolean revise(Variable variableUn, Set<Object> domaineUn, Variable variableDeux, Set<Object> domaineDeux){
        boolean delete = false;
        Set<Object> valeursASupprimer = new HashSet<>();
        for(Object valOne : domaineUn){
            boolean viable = false;

            for(Object valTwo : domaineDeux){
                boolean touteSatisfaites = true;

                for(Constraint constraint : binaryConstraints){
                    if(constraint.getScope().contains(variableUn) && constraint.getScope().contains(variableDeux)){
                        Map<Variable, Object> state = new HashMap<>();
                        state.put(variableUn,valOne);
                        state.put(variableDeux, valTwo);

                        if(!constraint.isSatisfiedBy(state)){
                            touteSatisfaites = false;
                            break;
                        }
                    }
                }
                if(touteSatisfaites){
                    viable = true;
                    break;
                }
            }
            if(!viable){
                valeursASupprimer.add(valOne);//si valOne n'est pas viable on l'aajoute sur l'ensemble de supprimé
                delete = true;//mise à jour delete pour indiquer que les valeurs non viable ont été supprimé du domaine
            }

        }
        domaineUn.removeAll(valeursASupprimer);//supprimer toutes les valeurs qui ne possédent pas de support
        return delete;
     }

     /**
      * Implementation de l'algorithme coherence d'arc
      * @param domaines ensemble de domaine d'évolution
      * @return un boolean indiquant si le csp est coherent
      */

      public boolean ac1(Map<Variable, Set<Object>> domaines) {
        boolean change  = false; //On met change à false tant qu'on trouve pas une valeur viable,dés qu'on trouve une valeur non viable on met à jour change à true et on modifie les domaines dans le quelle une valeur a été trouvé non viable
        
        if (!enforceNodeConsistency(domaines)) {
            return false; // si on trouve un domaine qui est vide c'est à dire que csp n'est pas coherant de noeud, cette étape garentit aussi que les contraintes unaires sont satisfaites
        }
        do {
            change = false;
            for(Variable variable1 : domaines.keySet()){
                Set<Object> domain = new HashSet<>(domaines.get(variable1));
                for(Variable variable2 : domaines.keySet()){
                    if(!variable2.equals(variable1) && revise(variable1, domain, variable2, domaines.get(variable2))){
                        change = true;
                    }
                } 
                domaines.put(variable1,domain);
            }
        }while(change);

        for(Variable variable: domaines.keySet()){
            if(domaines.get(variable).isEmpty()) {
                return false;
            }
        }
        return true;
    }

        /**
         * La methode renvoie les contraintes portant sur une seule variable
         * @return Set<Constraint>
         */
        public Set<Constraint> getUnaryConstraints() {
            return unaryConstraints;
        }

        /**
         * La methode renvoie les contraintes portant sur l'implication de deux variables
         * @return Set<Constraint>
         */
        public Set<Constraint> getBinaryConstraints() {
            return binaryConstraints;
        }

      
    
    
}
