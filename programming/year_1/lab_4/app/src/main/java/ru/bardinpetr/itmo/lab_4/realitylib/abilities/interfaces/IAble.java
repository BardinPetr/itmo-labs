package ru.bardinpetr.itmo.lab_4.realitylib.abilities.interfaces;

import ru.bardinpetr.itmo.lab_4.realitylib.abilities.Ability;
import ru.bardinpetr.itmo.lab_4.realitylib.abilities.errors.AbilityExistsException;
import ru.bardinpetr.itmo.lab_4.realitylib.abilities.errors.AbilityNotFoundException;
import ru.bardinpetr.itmo.lab_4.realitylib.abilities.errors.NotPureAbilityException;
import ru.bardinpetr.itmo.lab_4.realitylib.utils.InstantiationException;
import ru.bardinpetr.itmo.lab_4.realitylib.utils.InstantiationHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IAble {
    Map<String, Ability> getModifiedAbilities();

    Set<Class> getPureAbilities();

    /**
     * Adds an ability to pure group. Only one ability of each type can be added.
     *
     * @param ability ability to add
     */
    default void addAbility(Ability ability) {
        // This part allows deprecated ability names to be used as earlier.
        // Should be removed when getAbilityName support is dropped.
        if (!ability.getAbilityName().isEmpty()) {
            if (!ability.isPure())
                throw new NotPureAbilityException(ability);

            addAbility(ability.getAbilityName(), ability);
            return;
        }

        addAbility(ability.getClass());
    }

    default void addAbility(Class abilityClass) throws AbilityExistsException {
        var abilities = getPureAbilities();
        if (abilities.contains(abilityClass))
            throw new AbilityExistsException(abilityClass);

        abilities.add(abilityClass);
    }

    default void addAbility(String name, Ability ability) throws AbilityExistsException {
        Ability res = getModifiedAbilities().putIfAbsent(name, ability);
        if (res != null)
            throw new AbilityExistsException(name, res);
    }

    default void overrideAbility(String name, Ability ability) throws AbilityExistsException {
        getModifiedAbilities().put(name, ability);
    }


    /**
     * Returns all named abilities
     *
     * @return all abilities as List
     */
    default List<Ability> getAbilities() {
        return List.copyOf(getModifiedAbilities().values());
    }

    @Deprecated
    default Ability getAbility(String type) {
        for (Ability ability : getAbilities()) if (ability.getAbilityType().equals(type)) return ability;
        return null;
    }

    /**
     * Searches an ability with specific name in named and typed abilities
     *
     * @param name ability's name set with addAbility(String, Ability).
     *             Name in Ability object is Deprecated and not used here
     * @return found ability if exists
     */
    default Ability getAbilityByName(String name) throws AbilityNotFoundException {
        Ability res = getModifiedAbilities().get(name);
        if (res == null)
            throw new AbilityNotFoundException(AbilityNotFoundException.SearchType.NAME, name);
        return res;
    }

    default Ability getAbility(Class abilityClass) throws AbilityNotFoundException, InstantiationException {
        if (!getPureAbilities().contains(abilityClass)) {
            for (Ability ability : getAbilities())
                if (ability.getClass() == abilityClass) return ability;

            throw new AbilityNotFoundException(AbilityNotFoundException.SearchType.TYPE, abilityClass.getName());
        }
        return InstantiationHelper.instantiatePureAbility(abilityClass);
    }
}
