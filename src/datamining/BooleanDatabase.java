package datamining;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import modelling.BooleanVariable;

public class  BooleanDatabase {

    protected Set<BooleanVariable> items;
    protected List<Set<BooleanVariable>> transactions;

    public BooleanDatabase(Set<BooleanVariable> items) {
        this.items = items;
        this.transactions = new ArrayList<Set<BooleanVariable>>();
    }

    public Set<BooleanVariable> getItems() {
        return items;
    }


    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }


    public void add(Set<BooleanVariable> transaction) {
        this.transactions.add(transaction);
    }

    
    
}