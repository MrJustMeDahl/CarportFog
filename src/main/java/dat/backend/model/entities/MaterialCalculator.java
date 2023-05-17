package dat.backend.model.entities;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MaterialCalculator {

    private int length;
    private int width;
    private int minHeight;
    private final DecimalFormat df = new DecimalFormat("0.0");

    protected MaterialCalculator(int length, int width, int minHeight){
        this.length = length;
        this.width = width;
        this.minHeight = minHeight;
    }

    protected ItemListMaterial calculatePosts(List<Post> allPosts){
        Post chosenPost = null;
        int numberOfPosts = 4;
        for(int i = 0; i < allPosts.size(); i++){
            if(allPosts.get(i).length >= minHeight + 90){
                chosenPost = allPosts.get(i);
                break;
            }
        }
        if(length - 130 > 310 && length - 130 < 620){
            numberOfPosts += 2;
        } else if (length - 130 > 620) {
            numberOfPosts += 4;
        }
        if(width - 70 > 310){
            numberOfPosts += 2;
        }
        return new ItemListMaterial(chosenPost, numberOfPosts, "Stolper nedgraves 90 cm. i jord");
    }

    protected Set<ItemListMaterial> calculatePurlins(List<Purlin> allPurlins){
        Set<ItemListMaterial> purlins = new HashSet<>();
        Purlin chosenPurlin1 = null;
        Purlin chosenPurlin2 = null;
        int numberOfPurlins = 2;
        if(length <= 440){
            for(int i = 0; i < allPurlins.size(); i++){
                if(allPurlins.get(i).length >= length){
                    chosenPurlin1 = allPurlins.get(i);
                    break;
                }
            }
        } else if(length <= 750){
            for(int i = 0; i < allPurlins.size(); i++){
                if(allPurlins.get(i).length > ((length - 130)/2)){
                    chosenPurlin1 = allPurlins.get(i);
                    numberOfPurlins += 2;
                    break;
                }
            }
        } else {
            int midPartLength = (length - 130)/3;
            int purlin1Size = (length - 130 - (midPartLength*2)) + midPartLength + 30;
            int purlin2Size = midPartLength + 100;
            for(int i = 0; i < allPurlins.size(); i++){
                if(allPurlins.get(i).length > purlin2Size){
                    chosenPurlin2 = allPurlins.get(i);
                }
                if(allPurlins.get(i).length > purlin1Size){
                    chosenPurlin1 = allPurlins.get(i);
                    break;
                }
            }
        }
        purlins.add(new ItemListMaterial(chosenPurlin1, numberOfPurlins, "Remme i sider, sadles ned i stolper"));
        if(chosenPurlin2 != null){
            purlins.add(new ItemListMaterial(chosenPurlin2, numberOfPurlins, "Remme i sider, sadles ned i stolper"));
        }
        return purlins;
    }

    protected ItemListMaterial calculateRafters(List<Rafter> allRafters){
        Rafter chosenRafter = null;
        int numberOfRafters = (int) Math.ceil(length / 55)+1;
        for(int i = 0; i < allRafters.size(); i++){
            if(allRafters.get(i).length > width){
                chosenRafter = allRafters.get(i);
                break;
            }
        }
        return new ItemListMaterial(chosenRafter, numberOfRafters, "Spær monteres på rem - afstand mellem hvert spær: " + df.format(length/numberOfRafters) + "cm.");
    }
}
