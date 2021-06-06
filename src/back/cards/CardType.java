package back.cards;

import back.ThreatLevel;
import back.personalities.BasicPersonality;
import back.personalities.PersonalityAggressive;
import back.personalities.PersonalityCooperative;

/**
 * The type of a card helps to describe how this card should be played, in order
 * to use some cards before others.
 */
public enum CardType {
    TOOL(8, 8, new int[] { 100, 100, 100, 100 }, new int[] { 100, 100, 100, 100 }),
    USELESS(9, 9, new int[] { 0, 0, 0, 0 }, new int[] { 0, 0, 0, 0 }),
    WEAPON(0, 4, new int[] { 0, 0, 25, 100 }, new int[] { 0, 0, 0, 50 }),
    FOOD(3, 2, new int[] { 0, 0, 50, 100 }, new int[] { 5, 10, 25, 100 }),
    WATER(2, 1, new int[] { 0, 0, 50, 100 }, new int[] { 5, 10, 25, 100 }),
    WOOD(4, 3, new int[] { 0, 0, 50, 100 }, new int[] { 5, 10, 25, 100 }),
    SPY(7, 6, new int[] { 5, 5, 5, 5 }, new int[] { 5, 5, 5, 5 }),
    PROTECTION(1, 0, new int[] { 0, 0, 0, 100 }, new int[] { 0, 0, 0, 100 }),
    HELP(6, 5, new int[] { 0, 1, 2, 5 }, new int[] { 5, 7, 8, 10 }),
    THREAT(5, 7, new int[] { 5, 7, 8, 10 }, new int[] { 0, 1, 2, 5 });

    int aggressiveValuePriorityOrder;
    int cooperativeValuePriorityOrder;
    int[] aggressiveProbabilities;
    int[] cooperativeProbabilities;

    CardType(int aggressiveValuePriorityOrder, int cooperativeValuePriorityOrder, int[] aggressiveProbabilities,
            int[] cooperativeProbabilities) {
        this.aggressiveValuePriorityOrder = aggressiveValuePriorityOrder;
        this.cooperativeValuePriorityOrder = cooperativeValuePriorityOrder;
        this.aggressiveProbabilities = aggressiveProbabilities;
        this.cooperativeProbabilities = cooperativeProbabilities;
    }

    /**
     * The getter for the attribute {@link CardType#aggressiveValuePriorityOrder}.
     * 
     * @return the position of this type in an aggressive personality's priority
     *         order
     */
    public int getAggressiveValuePriorityOrder() {
        return aggressiveValuePriorityOrder;
    }

    /**
     * The getter for the attribute {@link CardType#cooperativeValuePriorityOrder}.
     * 
     * @return the position of this type in a cooperative personality's priority
     *         order
     */
    public int getCooperativeValuePriorityOrder() {
        return cooperativeValuePriorityOrder;
    }

    /**
     * Gets the probability to play this type of card for a given personality in a
     * given threat level.
     * 
     * @param personality the given personality
     * @param threatLevel the given threat level
     * @return the probability
     */
    public int getProbability(BasicPersonality personality, ThreatLevel threatLevel) {
        if (personality instanceof PersonalityCooperative) {
            return cooperativeProbabilities[threatLevel.getAssociatedIndex()];
        } else if (personality instanceof PersonalityAggressive) {
            return aggressiveProbabilities[threatLevel.getAssociatedIndex()];
        } else {
            return -1;
        }
    }
}
