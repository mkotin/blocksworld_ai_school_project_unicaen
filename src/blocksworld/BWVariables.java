package blocksworld;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import modelling.BooleanVariable;
import modelling.Variable;

/**
 * A class representing all blocksworld variables
 */
public class BWVariables {

    private int nbBlocks; // number of blocks
    private int nbPiles; // number of piles
    private Set<Variable> onbVaribles; // Onb variables
    private Set<Variable> fixedbVariables; // Fixedb Variables
    private Set<Variable> freepVariables; // Freep variables
    private Map<String, Variable> mapStr2Var = new HashMap<>();
    // set containing all the variables
    private Set<Variable> variables = new HashSet<>();
    
    /**
     * Constructore
     * @param nbBlocks
     * @param nbPiles
     */
    public BWVariables(int nbBlocks, int nbPiles) {
        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;
        
        createOnbVariables();
        createFixedbVariables();
        createFreepVariables();
        variables.addAll(onbVaribles);
        variables.addAll(fixedbVariables);
        variables.addAll(freepVariables);
    }

    /**
     * Method to create all onB variables
     */
    public void createOnbVariables() {
        this.onbVaribles = new HashSet<>();

        // Domain of onb variables
        Set<Object> domain = new HashSet<Object>();
        for (int i = -this.nbPiles; i < this.nbBlocks; i++) { // from -p piles to -b blocks
			domain.add(i);
		}

        // Creating variables
        for(int i=0; i<this.nbBlocks; i++) {
            Set<Object> domainI = new HashSet<Object>(domain);
            domainI.remove(i);
            
            Variable onbVariable = new Variable("on_" + Integer.toString(i), domainI);
            this.onbVaribles.add(onbVariable);
            mapStr2Var.put(onbVariable.getName(), onbVariable);
        }
    }

    /**
     * Method to create all fixedB variables
     */
    public void createFixedbVariables() {
        this.fixedbVariables = new HashSet<>();

        // Creating variables
        for(int i=0; i<this.nbBlocks; i++) {
            Variable fixedVariable = new BooleanVariable("fixed_" + Integer.toString(i));
            this.fixedbVariables.add(fixedVariable);
            mapStr2Var.put(fixedVariable.getName(), fixedVariable);
        }
    }

    /**
     * Method to create all freep variables
     */
    public void createFreepVariables() {
        this.freepVariables = new HashSet<>();

        // Creating variables
        for(int i=0; i<this.nbPiles; i++) {
            Variable freepVariable = new BooleanVariable("free_" + Integer.toString(i));
            this.freepVariables.add(freepVariable);
            mapStr2Var.put(freepVariable.getName(), freepVariable);
        }
    }

    /**
     * Static method to get index of a block or pile variable
     * @param var the variable representing a block or pile
     * @return the block or pile index
     */
    public static int getIndex(Variable var) {
        String varName = var.getName();
        return  varName.charAt(varName.length() -1) - '0';
    }

    public Map<Variable, Object> getState(int[][] state) {
        Map<Variable, Object> res = new HashMap<>();
        int nbPiles = state.length;
        for (int i = 0; i < nbPiles; i++) {
          int nbBlocks = state[i].length;
          String str = "free_" + Integer.toString(i);
          Variable free = mapStr2Var.get(str);
          if (nbBlocks == 0) {
            res.put(free, true);
          } else {
            res.put(free, false);
          }
          for (int j = 0; j < nbBlocks; j++) {
            String strOn = "on_" + Integer.toString(state[i][j]);
            String strfix = "fixed_" + Integer.toString(state[i][j]);
            Variable on = mapStr2Var.get(strOn);
            Variable fixed = mapStr2Var.get(strfix);
            if (j == 0) {
              res.put(on, -i - 1);
              res.put(fixed, true);
            }
            if (nbBlocks == 1) {
              res.put(on, -i - 1);
              res.put(fixed, false);
            }
            if (j > 0 && j == nbBlocks - 1) {
              res.put(on, state[i][j - 1]);
              res.put(fixed, false);
            }
            if (j > 0 && j < nbBlocks - 1) {
              res.put(on, state[i][j - 1]);
              res.put(fixed, true);
            }
    
          }
        }
        return res;
    }

    /**
     * Get variable by name
     * @param str name of variable
     * @return the variable
     */
    public Variable getVarByName(String str) {
        return mapStr2Var.get(str);
    }

    /**
     * Method to build onvar name of a block based on its index
     * @param index of the block
     * @return the name
     */
    public String buildOnBVarName(int index) {
        return "on_" + Integer.toString(index);
    }

    /**
     * Method to build fixed var name of a block based on its index
     * @param index of the block
     * @return the name
     */
    public String buildFixedBVarName(int index) {
        return "fixed_" + Integer.toString(index);
    }

    /**
     * Method to build free var name of a pile based on its index
     * @param index of the pile
     * @return the name
     */
    public String builFreePVarName(int index) {
        return "free_" + Integer.toString(index);
    }

    /**
     * Get onb var based on block index
     * @param index the index
     * @return the variable
     */
    public Variable getOnbVarByIndex(int index) {
        return mapStr2Var.get(buildOnBVarName(index));
    }
    
    /**
     * Get fixedb var based on block index
     * @param index the index
     * @return the variable
     */
    public Variable getFixedbVarByIndex(int index) {
        return mapStr2Var.get(buildFixedBVarName(index));
    }

    /**
     * Get freep var based on pile index
     * @param index the index
     * @return the variable
     */
    public Variable getFreepVarByIndex(int index) {
        return mapStr2Var.get(builFreePVarName(index));
    }


    // Getters and Setters
    /**
     * Return number of blocks
     * @return number of blocks
     */
    public int getNbBlocks() {
        return nbBlocks;
    }

    /**
     * Set number of blocks
     * @param nbBlocks
     */
    public void setNbBlocks(int nbBlocks) {
        this.nbBlocks = nbBlocks;
    }

    /**
     * Return number of piles
     * @return number of piles
     */
    public int getNbPiles() {
        return nbPiles;
    }

    /**
     * Set number of piles
     * @param nbPiles
     */
    public void setNbPiles(int nbPiles) {
        this.nbPiles = nbPiles;
    }

    /**
     * Get onb variables
     * @return onb variables
     */
    public Set<Variable> getOnbVaribles() {
        return onbVaribles;
    }

    /**
     * Set onb variables
     * @param onbVaribles
     */
    public void setOnbVaribles(Set<Variable> onbVaribles) {
        this.onbVaribles = onbVaribles;
    }

    /**
     * Return fixedb variables
     * @return fixedb variables
     */
    public Set<Variable> getFixedbVariables() {
        return fixedbVariables;
    }

    /**
     * Set fixeb variables
     * @param fixedbVariables
     */
    public void setFixedbVariables(Set<Variable> fixedbVariables) {
        this.fixedbVariables = fixedbVariables;
    }

    /**
     * Get freep variables
     * @return freep variables
     */
    public Set<Variable> getFreepVariables() {
        return freepVariables;
    }

    /**
     * Set freep variables
     * @param freepVariables
     */
    public void setFreepVariables(Set<Variable> freepVariables) {
        this.freepVariables = freepVariables;
    }

    public Set<Variable> getVariables() {
        return variables;
    }

    

    
}
