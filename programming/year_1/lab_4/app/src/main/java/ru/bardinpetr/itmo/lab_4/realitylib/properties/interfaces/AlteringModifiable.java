package ru.bardinpetr.itmo.lab_4.realitylib.properties.interfaces;

import ru.bardinpetr.itmo.lab_4.realitylib.properties.errors.ModifierNotEditableException;
import ru.bardinpetr.itmo.lab_4.realitylib.properties.errors.ModifierNotFoundException;
import ru.bardinpetr.itmo.lab_4.realitylib.utils.InstantiationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AlteringModifiable extends Modifiable {
    Map<Class, IAlteringModifier> getModifierMapping();

    @Override
    default List<IModifier> getModifiers() {
        return new ArrayList<>(getModifierMapping().values());
    }

    @Override
    default AlteringModifiable applyModifier(IModifier mod) {
        return setModifier(mod.getClass(), mod.getValue());
    }

    default AlteringModifiable setModifier(Class modClass, Object modValue) throws ModifierNotEditableException {
        var mods = getModifierMapping();
        if (!AlteringModifiable.class.isAssignableFrom(modClass))
            throw new ModifierNotEditableException(modClass);

        if (!mods.containsKey(modClass)) {
            getModifierMapping().put(modClass, (IAlteringModifier) InstantiationHelper.instantiateModifier(modClass));
        } else {
            getModifier(modClass).setValue(modValue);
        }
        return this;
    }

    default IAlteringModifier getModifier(Class modClass) {
        var mods = getModifierMapping();
        if (!mods.containsKey(modClass))
            throw new ModifierNotFoundException(modClass);
        return mods.get(modClass);
    }
}