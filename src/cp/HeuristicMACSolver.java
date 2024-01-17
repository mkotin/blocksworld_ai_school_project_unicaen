package cp;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;


/**
 * cette classe permet de définir une coherence de nœuds en basant sur une heuristique de valeur
 */
public class HeuristicMACSolver extends AbstractSolver{
    protected VariableHeuristic heuristicVariable;
    protected ValueHeuristic heuristicValeur;

    /**
     * Crée une instance de la classe
     * @param variables est un ensemble de variables
     * @param constraintes est un ensemble de contraintes
     * @param heuristicVariable une heuristique sur les variables
     * @param heuristicValue est une heuristique sur les domaines
     */
    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraintes, VariableHeuristic heuristicVariable, ValueHeuristic heuristicValue) {
        super(variables, constraintes);
        this.heuristicVariable = heuristicVariable;
        this.heuristicValeur = heuristicValue;
    }

    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> domaines = new HashMap<>();
        for (Variable variable : this.variables) {
            domaines.put(variable, variable.getDomain());
        }
        return this.mac(new HashMap<>(), new LinkedList<>(this.variables), domaines);
    }

    /**
     *Cette methode applique l'algorithme de BackTracking avec l'arc consistency
     * il réduit les domaines avant d'entamer la resolution du problème en fixant les
     * domaines des variables. Et se sert d'une heuristique pour choisir l'ordre des variables à instancier
     *
     * @param partialInstantiation l'affectation partielle
     * @param uninstancifiedVariable liste des variables non instancier
     * @param domaines l'ensemble des domaines
     * @return une solution qui étend la solution partielle si elle existe ou nul s'il n'y pas de solution
     */
    public Map<Variable, Object> mac(Map<Variable, Object> partialInstantiation, LinkedList<Variable> uninstancifiedVariable, Map<Variable, Set<Object>> domaines) {
        if (uninstancifiedVariable.isEmpty()){
            return partialInstantiation;
        }else {
            ArcConsistency arcConsistency = new ArcConsistency( this.contraintes);
            if (!arcConsistency.ac1(domaines)){
                return  null;
            }
            Variable variable = heuristicVariable.best(new HashSet<>(uninstancifiedVariable),domaines);
            uninstancifiedVariable.remove(variable);
            for (Object objet: heuristicValeur.ordering(variable,domaines.get(variable))){
                Map<Variable, Object> paritel = new HashMap<Variable, Object>(partialInstantiation);
                paritel.put(variable,objet);
                Map<Variable, Set<Object>> domainetmp = new HashMap<>();
                domainetmp.putAll(domaines);
                domainetmp.put(variable, new HashSet<>(Collections.singleton(objet)));
                if (isConsistent(paritel)){
                    Map<Variable,Object> resultat = this.mac(paritel,uninstancifiedVariable,domainetmp);
                    if (resultat!=null){
                        return resultat;
                    }
                }
            }
            uninstancifiedVariable.add(variable);
            return null;
        }
    }

    /**
     * Cette methode renvoie l'heuristique sur les variables
     * @return VariableHeuristic
     */
    public VariableHeuristic getHeuristicVariable() {
        return heuristicVariable;
    }

    /**
     * Cette methode renvoie l'heuristique sur le domaine
     * @return ValueHeuristic
     */
    public ValueHeuristic getHeuristicValeur() {
        return heuristicValeur;
    }
}