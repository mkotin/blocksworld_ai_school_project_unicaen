package modelling;

import java.util.HashSet;
import java.util.Set;

public class BooleanVariable extends Variable {

    public BooleanVariable(String name) {
        super(name, null);
        Set<Object> domain = new HashSet<Object>();
        domain.add(true);
        domain.add(false);
        super.setDomain(domain);;
    }

    
}