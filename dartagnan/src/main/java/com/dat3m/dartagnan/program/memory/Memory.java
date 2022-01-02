package com.dat3m.dartagnan.program.memory;

import com.dat3m.dartagnan.program.memory.utils.IllegalMemoryAccessException;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSet;

import static com.dat3m.dartagnan.expression.utils.Utils.convertToIntegerFormula;
import java.util.*;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.FormulaManager;
import org.sosy_lab.java_smt.api.IntegerFormulaManager;
import org.sosy_lab.java_smt.api.SolverContext;

public class Memory {

    private final BiMap<Location, Address> map;
    private final Map<String, Location> locationIndex;
    private final Map<String, List<Address>> arrays;

    private int nextIndex = 1;

    public Memory(){
        map = HashBiMap.create();
        locationIndex = new HashMap<>();
        arrays = new HashMap<>();
    }

    public Location getLocationForAddress(Address address){
        return map.inverse().get(address);
    }

    // Assigns each Address a fixed memory address.
    public BooleanFormula fixedMemoryEncoding(SolverContext ctx) {
        FormulaManager fmgr = ctx.getFormulaManager();
		IntegerFormulaManager imgr = fmgr.getIntegerFormulaManager();

    	BooleanFormula[] addrExprs = getAllAddresses().stream().filter(x -> !x.hasConstantValue())
        		.map(add -> imgr.equal(convertToIntegerFormula(add.toIntFormula(ctx), ctx),
        								imgr.makeNumber(add.getValue().intValue())))
        		.toArray(BooleanFormula[]::new);
        return fmgr.getBooleanFormulaManager().and(addrExprs);
    }

    public List<Address> malloc(String name, int size, int precision){
        if(!arrays.containsKey(name) && size > 0){
            List<Address> addresses = new ArrayList<>();
            for(int i = 0; i < size; i++){
                addresses.add(new Address(nextIndex++, precision));
            }
            arrays.put(name, addresses);
            return addresses;
        }
        throw new IllegalMemoryAccessException("Illegal malloc for " + name);
    }

    public Location getOrCreateLocation(String name, int precision){
        if(!locationIndex.containsKey(name)){
            Location location = new Location(name, new Address(nextIndex++, precision));
            map.put(location, location.getAddress());
            locationIndex.put(name, location);
            return location;
        }
        return locationIndex.get(name);
    }

    public ImmutableSet<Address> getAllAddresses(){
        Set<Address> result = new HashSet<>(map.values());
        for(List<Address> array : arrays.values()){
            result.addAll(array);
        }
        return ImmutableSet.copyOf(result);
    }

    public boolean isArrayPointer(Address address) {
    	return arrays.values().stream()
        	.collect(ArrayList::new, List::addAll, List::addAll).contains(address);
    }
    
    public Collection<List<Address>> getArrays() {
        return arrays.values();
    }
    
    public List<Address> getArrayfromPointer(Address address) {
    	for(List<Address> array : arrays.values()) {
    		if(array.contains(address)) {
    			return array;
    		}
    	}
    	// This method shall be called after isArrayPointer to avoid returning null
		return null;
    }
}