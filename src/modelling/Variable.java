package modelling;

import java.util.Set;

public class Variable {

    protected String name;
    protected Set<Object> domain;

    public Variable(String name, Set<Object> domain) {
        this.name = name;
        this.domain = domain;
    }

    

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public Set<Object> getDomain() {
        return domain;
    }



    public void setDomain(Set<Object> domain) {
        this.domain = domain;
    }



    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof Variable))
            return false;
        
        Variable v = (Variable) obj;

        return (this.name.equals(v.name));
    }



    @Override
    public int hashCode() {
        int prime = 31;
        return prime + (name == null ? 0 : name.hashCode());    
    }

    
}