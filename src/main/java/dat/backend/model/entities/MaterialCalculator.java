package dat.backend.model.entities;

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
    private final DecimalFormat df = new DecimalFormat("0.0");

    /**
     * @param length    length of carport in centimeter
     * @param width     width of carport in centimeter
     * @param minHeight minimum height of carport in centimeter
     */
    public MaterialCalculator(int length, int width, int minHeight, boolean hasShed) {
        this.length = length;
        this.width = width;
        this.minHeight = minHeight;
        this.hasShed = hasShed;
    }

    /**
     * Calculates which post fulfill the requirements for the carport and shed if the carport has a shed.
     * @param allPosts List of posts to choose from.
     * @return Set of ItemListMaterial - the material here is the same, but the message and amounts differs depending on if the carport has a shed.
     * @author MrJustMeDahl
     */
    public Set<ItemListMaterial> calculatePosts(List<Post> allPosts) {
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
        posts.add(new ItemListMaterial(chosenPost, numberOfPosts, "Stolper nedgraves 90 cm. i jord", "carport"));
        if (hasShed) {
            if (width - 70 > 310) {
                posts.add(new ItemListMaterial(chosenPost, 3, "Stolper nedgraves 90 cm. i jord", "shed"));
            } else {
                posts.add(new ItemListMaterial(chosenPost, 1, "Stolper nedgraves 90 cm. i jord", "shed"));
            }
        }
        return posts;
    }

    /**
     * Calculates which purlins fulfill the requirements for the carport.
     * @param allPurlins List of purlins to choose from.
     * @return Set of ItemListMaterial - if the length of the carport is long enough, 2 different purlins are required, the length of these purlins will be calculated towards being assembled on top of the middle post.
     * @author MrJustMeDahl
     */
    public Set<ItemListMaterial> calculatePurlins(List<Purlin> allPurlins) {
        Set<ItemListMaterial> purlins = new HashSet<>();
        Purlin chosenPurlin1 = null;
        Purlin chosenPurlin2 = null;
        int numberOfPurlins = 2;
        if (length <= 440) {
            for (int i = 0; i < allPurlins.size(); i++) {
                if (allPurlins.get(i).length >= length) {
                    chosenPurlin1 = allPurlins.get(i);
                    break;
                }
            }
        } else if (length <= 750) {
            for (int i = 0; i < allPurlins.size(); i++) {
                if (allPurlins.get(i).length > ((length - 130) / 2)) {
                    chosenPurlin1 = allPurlins.get(i);
                    numberOfPurlins += 2;
                    break;
                }
            }
        } else {
            int midPartLength = (length - 130) / 3;
            int purlin1Size = (length - 130 - (midPartLength * 2)) + midPartLength + 30;
            int purlin2Size = midPartLength + 100;
            for (int i = 0; i < allPurlins.size(); i++) {
                if (allPurlins.get(i).length > purlin2Size) {
                    chosenPurlin2 = allPurlins.get(i);
                }
                if (allPurlins.get(i).length > purlin1Size) {
                    chosenPurlin1 = allPurlins.get(i);
                    break;
                }
            }
        }
        purlins.add(new ItemListMaterial(chosenPurlin1, numberOfPurlins, "Remme i sider, sadles ned i stolper", "carport"));
        if (chosenPurlin2 != null) {
            purlins.add(new ItemListMaterial(chosenPurlin2, numberOfPurlins, "Remme i sider, sadles ned i stolper", "carport"));
        }
        return purlins;
    }

    /**
     * Calculates which rafter fulfill the requirements for the carport.
     * @param allRafters List of purlins to choose from.
     * @return ItemListMaterial which contains the chosen rafter and how many are needed for the size.
     * @author MrJustMeDahl
     */
    public ItemListMaterial calculateRafters(List<Rafter> allRafters) {
        Rafter chosenRafter = null;
        int numberOfRafters = (int) Math.ceil(length / 55) + 1;
        for (int i = 0; i < allRafters.size(); i++) {
            if (allRafters.get(i).length > width) {
                chosenRafter = allRafters.get(i);
                break;
            }
        }
        return new ItemListMaterial(chosenRafter, numberOfRafters, "Spær monteres på rem - afstand mellem hvert spær: " + df.format(length / numberOfRafters) + "cm.", "carport");
    }
}
