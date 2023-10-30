package me.weebo.managers.scenarios;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import me.weebo.scenarios.Enchanter;
import me.weebo.scenarios.NoFire;
import me.weebo.scenarios.PreEnchants;
import me.weebo.scenarios.Soup;


public class ScenarioManager {
	
	@Getter
	List<Scenario> scenarios;

	public ScenarioManager() {
		scenarios = new ArrayList<Scenario>();
		scenarios.add(new NoFire());
		scenarios.add(new PreEnchants());
		scenarios.add(new Soup());
		scenarios.add(new Enchanter());
	}
	public List<Scenario> getActiveScenarios() {
    	final List<Scenario> list = new ArrayList<Scenario>();
    	for (final Scenario scenario: scenarios) {
    		if (scenario.isActive()) {
    			list.add(scenario);
    		}
    	}
    	return list;
    }
	public List<String> getActiveScenarioNames() {
    	final List<String> list = new ArrayList<String>();
    	for (final Scenario scenario: scenarios) {
    		if (scenario.isActive()) {
    			list.add(scenario.getName());
    		}
    	}
    	return list;
    }
	public Scenario getScenario(String name) {
		for (Scenario scenario: scenarios) {
			if (name.equalsIgnoreCase(scenario.getName().replaceAll(" ", ""))) {
				return scenario;
			}
		}
		return null;
	}

}
