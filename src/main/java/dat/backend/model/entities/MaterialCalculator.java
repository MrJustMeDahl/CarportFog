package dat.backend.model.entities;

import dat.backend.model.exceptions.NoMaterialFoundException;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is used to calculate the contents of an itemlist.
 * An instance is created for every itemlist.
 *
 * @author MrJustMeDahl
 */
public class MaterialCalculator {

    private int length;
    private int width;
    private int minHeight;
    private boolean hasShed;
    private int shedLength;
    private int shedWidth;
    private final DecimalFormat df = new DecimalFormat("0.0");

    /**
     * @param length    length of carport in centimeter
     * @param width     width of carport in centimeter
     * @param minHeight minimum height of carport in centimeter
     */
    public MaterialCalculator(int length, int width, int minHeight, boolean hasShed, int shedLength, int shedWidth) {
        this.length = length;
        this.width = width;
        this.minHeight = minHeight;
        this.hasShed = hasShed;
        this.shedLength = shedLength;
        this.shedWidth = shedWidth;
    }

    /**
     * Calculates which post fulfill the requirements for the carport and shed if the carport has a shed.
     * @param allPosts List of posts to choose from.
     * @return Set of ItemListMaterial - the material here is the same, but the message and amounts differs depending on if the carport has a shed.
     * @throws NoMaterialFoundException Is thrown if there is no material in the list that fulfill the requirements.
     * @author MrJustMeDahl
     */
    public Set<ItemListMaterial> calculatePosts(List<Post> allPosts) throws NoMaterialFoundException{
        Set<ItemListMaterial> posts = new HashSet<>();
        Post chosenPost = null;
        int numberOfPosts = 4;
        for (int i = 0; i < allPosts.size(); i++) {
            if (allPosts.get(i).length >= minHeight + 90 + 10) {
                chosenPost = allPosts.get(i);
                break;
            }
        }
        if (length - 130 > 310 && length - 130 < 620) {
            numberOfPosts += 2;
        } else if (length - 130 > 620) {
            numberOfPosts += 4;
        }
        if(chosenPost == null){
            throw new NoMaterialFoundException("Could not find a post that matches the measurements of the selected carport");
        }
        posts.add(new ItemListMaterial(chosenPost, numberOfPosts, "Stolper nedgraves 90 cm. i jord", "carport", minHeight));
        if (hasShed) {
            if (width - 70 > 310) {
                posts.add(new ItemListMaterial(chosenPost, 3, "Stolper nedgraves 90 cm. i jord", "shed", minHeight));
            } else {
                posts.add(new ItemListMaterial(chosenPost, 1, "Stolper nedgraves 90 cm. i jord", "shed", minHeight));
            }
        }
        return posts;
    }

    /**
     * Calculates which purlins fulfill the requirements for the carport.
     * @param allPurlins List of purlins to choose from.
     * @return Set of ItemListMaterial - if the length of the carport is long enough, 2 different purlins are required, the length of these purlins will be calculated towards being assembled on top of the middle post.
     * @throws NoMaterialFoundException Is thrown if there is no material in the list that fulfill the requirements.
     * @author MrJustMeDahl
     */
    public Set<ItemListMaterial> calculatePurlins(List<Purlin> allPurlins) throws NoMaterialFoundException{
        Set<ItemListMaterial> purlins = new HashSet<>();
        Purlin chosenPurlin1 = null;
        Purlin chosenPurlin2 = null;
        int numberOfPurlins = 2;
        int purlin1Size = 0;
        int purlin2Size = 0;
        if (length <= 440) {
            for (int i = 0; i < allPurlins.size(); i++) {
                if (allPurlins.get(i).length >= length) {
                    chosenPurlin1 = allPurlins.get(i);
                    purlin1Size = length;
                    break;
                }
            }
        }else if (length <= 600){
            for (int i = 0; i < allPurlins.size(); i++) {
                if (allPurlins.get(i).length >= length) {
                    chosenPurlin1 = allPurlins.get(i);
                    purlin1Size = length;
                    break;
                }
            }
        } else if (length <= 750) {
            for (int i = 0; i < allPurlins.size(); i++) {
                if (allPurlins.get(i).length >= ((length - 130) / 2) + 100) {
                    chosenPurlin1 = allPurlins.get(i);
                    purlin1Size = ((length - 130) / 2) + 100;
                    break;
                }
            }
            for (int i = 0; i < allPurlins.size(); i++) {
                if (allPurlins.get(i).length >= ((length - 130) / 2) + 30) {
                    chosenPurlin2 = allPurlins.get(i);
                    purlin2Size = ((length - 130) / 2) + 30;
                    break;
                }
            }
        } else {
            int midPartLength = (length - 130) / 3;
            purlin1Size = (length - 130 - (midPartLength * 2)) + midPartLength + 30;
            purlin2Size = midPartLength + 100;
            for (int i = 0; i < allPurlins.size(); i++) {
                if (allPurlins.get(i).length >= purlin2Size) {
                    chosenPurlin2 = allPurlins.get(i);
                    break;
                }
            }
            for (int i = 0; i < allPurlins.size(); i++) {
                if (allPurlins.get(i).length >= purlin1Size) {
                    chosenPurlin1 = allPurlins.get(i);
                    break;
                }
            }
        }
        if(chosenPurlin1 == null || (chosenPurlin2 == null && length > 750)){
            throw new NoMaterialFoundException("Could not find a purlin that matches the measurements of the selected carport");
        }
        purlins.add(new ItemListMaterial(chosenPurlin1, numberOfPurlins, "Remme i sider, sadles ned i stolper", "carport", purlin1Size));
        if (chosenPurlin2 != null) {
            purlins.add(new ItemListMaterial(chosenPurlin2, numberOfPurlins, "Remme i sider, sadles ned i stolper", "carport", purlin2Size));
        }
        return purlins;
    }

    /**
     * Calculates which rafter fulfill the requirements for the carport.
     * @param allRafters List of rafters to choose from.
     * @return ItemListMaterial which contains the chosen rafter and how many are needed for the size.
     * @throws NoMaterialFoundException Is thrown if there is no material in the list that fulfill the requirements.
     * @author MrJustMeDahl
     */
    public ItemListMaterial calculateRafters(List<Rafter> allRafters) throws NoMaterialFoundException{
        Rafter chosenRafter = null;
        int numberOfRafters = (int) Math.ceil(length / 55) + 1;
        for (int i = 0; i < allRafters.size(); i++) {
            if (allRafters.get(i).length >= width) {
                chosenRafter = allRafters.get(i);
                break;
            }
        }
        if(chosenRafter == null){
            throw new NoMaterialFoundException("Could not find a rafter that matches the measurements of the selected carport");
        }
        return new ItemListMaterial(chosenRafter, numberOfRafters, "Spær monteres på rem - afstand mellem hvert spær: " + df.format(length / numberOfRafters) + "cm.", "carport", width);
    }

    public boolean getHasShed() {
        return hasShed;
    }

    /**
     * Calculates which roofs fulfill the requirements for the carport.
     * @param allRoofs List of sheathings to choose from.
     * @return ItemListMaterial which contains the chosen roof and how many are needed for the size.
     * @author MrJustMeDahl
     */
    public Set<ItemListMaterial> calculateRoofs(List<Roof> allRoofs) {
        Set<ItemListMaterial> roofs = new HashSet<>();
        Roof chosenRoof = null;
        int actualNumberOfRoofsNeeded = 0;
        int actualNumberOfRowsNeeded = 0;
        double lowestWaste = 0;
        for(Roof r: allRoofs){
            int numberOfRoofsNeeded = 0;
            int numberOfRowsNeeded = 1;
            double waste = 0;
            double wasteWidth = 0;
            for(int i = 0; i < width; i += 100){
                numberOfRoofsNeeded++;
                if(i + 80 >= width){
                    wasteWidth = ((i + 80 - width)/100) * (r.getLength()/100);
                }
                if(i != 0) {
                    i -= 20;
                }
            }
            int numberOfRoofsPerRow = numberOfRoofsNeeded;
            double wasteLength = 0;
            for(int i = r.getLength(); i < length; i += r.getLength()){
                numberOfRoofsNeeded += numberOfRoofsPerRow;
                numberOfRowsNeeded++;
                if(i + r.getLength() - 20 >= length){
                    wasteLength = ((i + r.getLength() - 20 - length)/100) * numberOfRoofsPerRow;
                }
                i -= 20;
            }
            waste = (wasteWidth * (numberOfRoofsNeeded/numberOfRoofsPerRow)) + wasteLength;
            if(waste < lowestWaste || lowestWaste == 0){
                lowestWaste = waste;
                chosenRoof = r;
                actualNumberOfRoofsNeeded = numberOfRoofsNeeded;
                actualNumberOfRowsNeeded = numberOfRowsNeeded;
            }
        }
        roofs.add(new ItemListMaterial(chosenRoof, actualNumberOfRoofsNeeded, "Tagplader moneters på spær", "carport", length/actualNumberOfRowsNeeded));
        return roofs;
    }

    /**
     * Calculates which rafter fulfill the requirements for the carport.
     * @param allSheathings List of sheathings to choose from.
     * @return ItemListMaterial which contains the chosen sheathing and how many are needed for the size.
     * @author MrJustMeDahl
     */
    public Set<ItemListMaterial> calculateSheathings(List<Sheathing> allSheathings) {
        Set<ItemListMaterial> sheathings = new HashSet<>();
        Sheathing chosenSheathing = null;
        int numberOfSheathings = (int) Math.ceil((2*(shedLength/8.5) + 2*(shedWidth/8.5)) + 4);
        int actualLength = minHeight;
        for(Sheathing s: allSheathings){
            if(s.getLength() >= minHeight){
                chosenSheathing = s;
                break;
            }
        }
        sheathings.add(new ItemListMaterial(chosenSheathing, numberOfSheathings, "Til beklædning af skur", "shed", actualLength));
        sheathings.add(new ItemListMaterial(new UnspecifiedMaterial(17, 38, "45x95mm. Reglar ubh.", "træ", "løsholter", 16.95, shedLength, 16.95*(shedLength/100)), 4, "Løsholter til skur sider", "shed", shedLength));
        sheathings.add(new ItemListMaterial(new UnspecifiedMaterial(17, 38, "45x95mm. Reglar ubh.", "træ", "løsholter", 16.95, shedWidth, 16.95*(shedWidth/100)), 6, "Løsholter til skur sider", "shed", shedWidth));
        sheathings.add(new ItemListMaterial(new UnspecifiedMaterial(17, 38, "38x73mm. lægte ubh.", "træ", "dør", 11.95, 420, 11.95*(420/100)), 1, "Lægte til Z på bagside af dør til skur", "shed", 420));
        return sheathings;
    }
}
