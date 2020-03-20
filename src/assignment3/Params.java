package assignment3;

import java.util.Random;

public class Params {
    static Random rnd = new Random();

    static final int KNIGHT_COUNT = 2;

    // average interval to produce quest
    static final int QUEST_ADDITION_TIME = 800;
    // average interval King Arthur to acquire quest
    static final int QUEST_ACQUIRE_TIME = 1000;
    // average interval King Arthur to discuss adventure
    static final int DISCUSS_ADVENTURE_TIME = 1000;
    // average interval King Arthur to complete quest
    static final int COMPLETE_QUEST_TIME = 1000;
    // average duration that knights spend mingling before and after meetings
    static final int MEAN_MINGLING_TIME = 200;

    // generate a random mingling duration
    static int getMinglingTime() {
        return (int) Math.max(0.0, rnd.nextGaussian() *
                MEAN_MINGLING_TIME / 6 + MEAN_MINGLING_TIME);
    }

    // generate a random interval for King Arthur to  discuss adventure
    public static int getKingDiscussAdventureTime() {
        return (int) Math.max(0.0, (rnd.nextGaussian() *
                DISCUSS_ADVENTURE_TIME / 8) + DISCUSS_ADVENTURE_TIME);
    }

    // generate a random interval for King Arthur to  complete quest
    public static int getKingCompleteQuestTime() {
        return (int) Math.max(0.0, (rnd.nextGaussian() *
                COMPLETE_QUEST_TIME / 8) + COMPLETE_QUEST_TIME);
    }

    // generate a random interval for King Arthur to  acquire quest
    public static int getKingAcquireQuestTime() {
        return (int) Math.max(0.0, (rnd.nextGaussian() *
                QUEST_ACQUIRE_TIME / 8) + QUEST_ACQUIRE_TIME);
    }

    // generate a random interval to produce quest
    public static int getProduceQuestTime() {
        return (int) Math.max(0.0, (rnd.nextGaussian() *
                QUEST_ADDITION_TIME / 8) + QUEST_ADDITION_TIME);
    }
}
