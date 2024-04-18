package DTO;

import entity.Person;


public class PersonDTO {

    private long id;

    private  String name;

    private String enrichment;

    public PersonDTO() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEnrichment() {
        return enrichment;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnrichment(String enrichment) {
        this.enrichment = enrichment;
    }

    public PersonDTO convertPersonDTO(Person person){

        PersonDTO personDTO=new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setEnrichment("Entity Enrichment via DTO");



        return personDTO;
    }


}









