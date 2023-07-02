/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities;

import java.util.List;

/**
 *
 * @author annelkaren
 */
public class Usuario {
    
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String fullname;
    private String email;
    private String address;
    private String phone1;
    private String phone2;
    private String icq;
    private String skype;
    private String yahoo;
    private String aim;
    private String msn;
    private String department;
    private String institution;
    private String idnumber;
    private int interests;
    private int firstaccess;
    private String lastaccess;
    private String description;
    private int descriptionformat;
    private String city;
    private String url;
    private String country;
    private String profileimageurlsmall;
    private String profileimageurl;
    private List<CustomField> customFields; 
    private List<Grupo> groups; 
    private List<Rol> roles;
    private List<Preferencia> preferences;
    private List<Curso> enrolledcourses;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phone1
     */
    public String getPhone1() {
        return phone1;
    }

    /**
     * @param phone1 the phone1 to set
     */
    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    /**
     * @return the phone2
     */
    public String getPhone2() {
        return phone2;
    }

    /**
     * @param phone2 the phone2 to set
     */
    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    /**
     * @return the icq
     */
    public String getIcq() {
        return icq;
    }

    /**
     * @param icq the icq to set
     */
    public void setIcq(String icq) {
        this.icq = icq;
    }

    /**
     * @return the skype
     */
    public String getSkype() {
        return skype;
    }

    /**
     * @param skype the skype to set
     */
    public void setSkype(String skype) {
        this.skype = skype;
    }

    /**
     * @return the yahoo
     */
    public String getYahoo() {
        return yahoo;
    }

    /**
     * @param yahoo the yahoo to set
     */
    public void setYahoo(String yahoo) {
        this.yahoo = yahoo;
    }

    /**
     * @return the aim
     */
    public String getAim() {
        return aim;
    }

    /**
     * @param aim the aim to set
     */
    public void setAim(String aim) {
        this.aim = aim;
    }

    /**
     * @return the msn
     */
    public String getMsn() {
        return msn;
    }

    /**
     * @param msn the msn to set
     */
    public void setMsn(String msn) {
        this.msn = msn;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the institution
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * @param institution the institution to set
     */
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    /**
     * @return the idnumber
     */
    public String getIdnumber() {
        return idnumber;
    }

    /**
     * @param idnumber the idnumber to set
     */
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    /**
     * @return the interests
     */
    public int getInterests() {
        return interests;
    }

    /**
     * @param interests the interests to set
     */
    public void setInterests(int interests) {
        this.interests = interests;
    }

    /**
     * @return the firstaccess
     */
    public int getFirstaccess() {
        return firstaccess;
    }

    /**
     * @param firstaccess the firstaccess to set
     */
    public void setFirstaccess(int firstaccess) {
        this.firstaccess = firstaccess;
    }

    /**
     * @return the lastaccess
     */
    public String getLastaccess() {
        return lastaccess;
    }

    /**
     * @param lastaccess the lastaccess to set
     */
    public void setLastaccess(String lastaccess) {
        this.lastaccess = lastaccess;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the descriptionformat
     */
    public int getDescriptionformat() {
        return descriptionformat;
    }

    /**
     * @param descriptionformat the descriptionformat to set
     */
    public void setDescriptionformat(int descriptionformat) {
        this.descriptionformat = descriptionformat;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the profileImageUrlSmall
     */
    public String getProfileimageurlsmall() {
        return profileimageurlsmall;
    }

    /**
     * @param profileImageUrlSmall the profileImageUrlSmall to set
     */
    public void setProfileimageurlsmall(String profileImageUrlSmall) {
        this.profileimageurlsmall = profileImageUrlSmall;
    }

    /**
     * @return the profileImageUrl
     */
    public String getProfileimageurl() {
        return profileimageurl;
    }

    /**
     * @param profileImageUrl the profileImageUrl to set
     */
    public void setProfileimageurl(String profileImageUrl) {
        this.profileimageurl = profileImageUrl;
    }

    /**
     * @return the preferences
     */
    public List<Preferencia> getPreferences() {
        return preferences;
    }

    /**
     * @param preferences the preferences to set
     */
    public void setPreferences(List<Preferencia> preferences) {
        this.preferences = preferences;
    }

    /**
     * @return the roles
     */
    public List<Rol> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    /**
     * @return the enrolledCourses
     */
    public List<Curso> getEnrolledcourses() {
        return enrolledcourses;
    }

    /**
     * @param enrolledCourses the enrolledCourses to set
     */
    public void setEnrolledcourses(List<Curso> enrolledcourses) {
        this.enrolledcourses = enrolledcourses;
    }

    /**
     * @return the groups
     */
    public List<Grupo> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<Grupo> groups) {
        this.groups = groups;
    }

    /**
     * @return the customFields
     */
    public List<CustomField> getCustomFields() {
        return customFields;
    }

    /**
     * @param customFields the customFields to set
     */
    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
