package kellehj1.FYP.birdID;

import java.util.HashMap;

public class ModelBird {
    private String name;
    private String description;
    private HashMap<String, Integer> sections;
//    private int beak;
//    private int leg;
//    private int crown;
//    private int cheek;
//    private int eyeStripe;
//    private int breast;
//    private int belly;
//    private int wing;
//    private int wingStripe;

    public ModelBird(String name, String description, HashMap<String, Integer> sections) {
        this.name = name;
        this.description = description;
        this.sections = sections;
//        this.beak = beak;
//        this.leg = leg;
//        this.crown = crown;
//        this.cheek = cheek;
//        this.eyeStripe = eyeStripe;
//        this.breast = breast;
//        this.belly = belly;
//        this.wing = wing;
//        this.wingStripe = wingStripe;
    }

    @Override
    public String toString() {
        return "ModelBird{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sections=" + sections +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, Integer> getSections() {
        return sections;
    }

    public void setSections(HashMap<String, Integer> sections) {
        this.sections = sections;
    }

    //    public int getBeak() {
//        return beak;
//    }
//
//    public void setBeak(int beak) {
//        this.beak = beak;
//    }
//
//    public int getLeg() {
//        return leg;
//    }
//
//    public void setLeg(int leg) {
//        this.leg = leg;
//    }
//
//    public int getCrown() {
//        return crown;
//    }
//
//    public void setCrown(int crown) {
//        this.crown = crown;
//    }
//
//    public int getCheek() {
//        return cheek;
//    }
//
//    public void setCheek(int cheek) {
//        this.cheek = cheek;
//    }
//
//    public int getEyeStripe() {
//        return eyeStripe;
//    }
//
//    public void setEyeStripe(int eyeStripe) {
//        this.eyeStripe = eyeStripe;
//    }
//
//    public int getBreast() {
//        return breast;
//    }
//
//    public void setBreast(int breast) {
//        this.breast = breast;
//    }
//
//    public int getBelly() {
//        return belly;
//    }
//
//    public void setBelly(int belly) {
//        this.belly = belly;
//    }
//
//    public int getWing() {
//        return wing;
//    }
//
//    public void setWing(int wing) {
//        this.wing = wing;
//    }
//
//    public int getWingStripe() {
//        return wingStripe;
//    }
//
//    public void setWingStripe(int wingStripe) {
//        this.wingStripe = wingStripe;
//    }
}
