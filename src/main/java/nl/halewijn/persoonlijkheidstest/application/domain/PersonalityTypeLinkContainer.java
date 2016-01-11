package nl.halewijn.persoonlijkheidstest.application.domain;

import java.util.List;

public class PersonalityTypeLinkContainer {

    private PersonalityType personalityType;
    private List<PersonalityTypeLink> links;

    public PersonalityTypeLinkContainer(PersonalityType personalityType, List<PersonalityTypeLink> links) {
        this.personalityType = personalityType;
        this.links = links;
    }

    public PersonalityType getPersonalityType() {
        return personalityType;
    }

    public List<PersonalityTypeLink> getLinks() {
        return links;
    }

}
