package ru.bardinpetr.itmo.lab_4.realitylib.story;

import ru.bardinpetr.itmo.lab_4.realitylib.abilities.interfaces.Describable;
import ru.bardinpetr.itmo.lab_4.realitylib.creatures.humans.Human;
import ru.bardinpetr.itmo.lab_4.realitylib.creatures.humans.HumanGroup;
import ru.bardinpetr.itmo.lab_4.realitylib.scenarios.Scenario;
import ru.bardinpetr.itmo.lab_4.realitylib.story.annotations.processors.*;
import ru.bardinpetr.itmo.lab_4.realitylib.things.PhysicalObject;

import java.util.HashMap;
import java.util.Map;

public abstract class Story {
    private final String storyName;
    private final Map<String, Human> actors = new HashMap<>();
    private final Map<String, HumanGroup> groups = new HashMap<>();
    private final Map<String, PhysicalObject> environment = new HashMap<>();
    private final Map<String, Scenario> scenarios = new HashMap<>();
    private final Map<String, Story> subStories = new HashMap<>();

    private final Map<String, ?>[] storages = new Map[]{actors, groups, environment, scenarios, subStories};

    public Story(String storyName) {
        this.storyName = storyName;
    }

    public String getStoryName() {
        return storyName;
    }

    static void describeMany(StringBuilder sb, Map data) {
        for (var i : data.values())
            sb
                    .append(">")
                    .append(((Describable) i).describe())
                    .append("\n");
    }

    public final void addActor(String internalName, Human other) {
        actors.put(internalName, other);
    }

    public final void addGroup(String internalName, HumanGroup other) {
        groups.put(internalName, other);
    }

    public final void addEnvironment(String internalName, PhysicalObject other) {
        environment.put(internalName, other);
    }

    public final void addScenario(String internalName, Scenario other) {
        scenarios.put(internalName, other);
    }

    public final Human getActor(String name) {
        return actors.get(name);
    }

    public final HumanGroup getGroup(String name) {
        return groups.get(name);
    }

    public final PhysicalObject getEnvironment(String name) {
        return environment.get(name);
    }

    public final Scenario getScenario(String name) {
        return scenarios.get(name);
    }

    public final Story getSubStory(String name) {
        return subStories.get(name);
    }

    public Map<String, Human> getActors() {
        return actors;
    }

    public Map<String, HumanGroup> getGroups() {
        return groups;
    }

    public Map<String, PhysicalObject> getEnvironment() {
        return environment;
    }

    public Map<String, Scenario> getScenarios() {
        return scenarios;
    }

    public Map<String, Story> getSubStories() {
        return subStories;
    }

    // DI- and annotation- related:

    public CompiledStory compile() {
        StoryItemProviderProcessor.process(this, this);
        AbleProcessor.process(this);
        SetupMethodProcessor.process(this);
        CreateScenarioProcessor.process(this);
        return new CompiledStory(this);
    }

    public final void addSubStory(String internalName, Story story) {
        subStories.put(internalName, story);
        StoryItemProviderProcessor.process(this, story);
        StoryInjectProcessor.process(this, story);
        story.compile();
    }

    public final Object getProvidedObject(String identifier, Class targetClass) {
        for (Map<String, ?> storage : storages) {
            Object val = storage.get(identifier);
            if (val == null) continue;
            if (targetClass.isAssignableFrom(val.getClass()))
                return targetClass.cast(val);
        }
        return null;
    }

    public static class CompiledStory {

        private final Story story;

        public CompiledStory(Story story) {
            this.story = story;
        }

        public String tell() {
            var sb = new StringBuilder("(STORY): %s: \n".formatted(story.getStoryName()));

            sb.append("\n(STORY): Окружение:\n");
            describeMany(sb, story.getEnvironment());

            sb.append("\n(STORY): Участники:\n");
            describeMany(sb, story.getActors());

            sb.append("\n(STORY): Группы:\n");
            describeMany(sb, story.getGroups());

            sb.append("\n(STORY): Сценарии:\n");
            describeMany(sb, story.getScenarios());

            return sb.toString();
        }
    }
}
