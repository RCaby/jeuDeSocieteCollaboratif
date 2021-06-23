import java.util.*;

public class Strings extends ListResourceBundle {
        public Object[][] getContents() {
                return contents;
        }

        private Object[][] contents = { { "hidden_card_label", "Card !" }, { "not_chief_label", "     " },
                        { "chief_label", "Chief" }, { "NoTarget", "%s uses the card %s." }, { "card", "Card" },
                        { "hiddenCardDescription", "A hidden card." }, { "OneTarget", "%s uses the card %s on %s." },
                        { "TwoTargets", "%s uses the card %s on %s and on %s." },
                        { "ThreeTargets", "%s uses the card %s on %s, on %s and on %s." },
                        { "UselessCard", "%s has used an useless card." }, { "RevealsCard", "%s reveals his card %s." },

                        { "AlarmClock_name", "Alarm Clock" },
                        { "AlarmClock_description",
                                        "Allows to choose the player who will play first during the next round." },
                        { "AlarmClock_smallDescription", "%s will be the next chief !" },

                        { "Antivenom_name", "Anti-venom" },
                        { "Antivenom_description", "Cures one player of their snake bite." },
                        { "Antivenom_smallDescription", "%s is now cured !" },

                        { "Axe_name", "Axe" },
                        { "Axe_description", "Allows to get two free plank fragments during the wood action." },
                        { "Axe_smallDescription", "%s will get two free wood fragments instead of one !" },

                        { "Barometer_name", "Barometer" },
                        { "Barometer_description", "Allows to see the weather of the next two rounds." },
                        { "Barometer_smallDescription", "%s has seen the weather of the next two rounds !" },

                        { "BoardGameQuoridor_name", "Board Game Quoridor" },
                        { "BoardGameQuoridor_description",
                                        "This object is useless, but at least you have a new, fun way to spend time." },

                        { "Cartridge_name", "Cartridge" },
                        { "Cartridge_description", "To be associated with the gun. Discarded after utilization." },
                        { "Cartridge_smallDescription", "%s has shot someone !" },

                        { "Club_name", "Club" },
                        { "Club_description",
                                        "Allows to vote twice during a voting session.\nPermanent use. Card discarded after the death of its owner." },
                        { "Club_smallDescription", "%s will now vote twice in each voting session." },

                        { "Coconut_name", "Coconut" },
                        { "Coconut_description", "Is equivalent to three water rations." },
                        { "Coconut_smallDescription", "%s has earned three water rations." },

                        { "Coffee_name", "Coffee" },
                        { "Coffee_description", "Allows to play two actions during this round." },
                        { "Coffee_smallDescription", "%s will play twice during this round." },

                        { "Conch_name", "Conch" },
                        { "Conch_description",
                                        "Makes its owner the chief and protects from any vote. Can be used before or after a vote." },
                        { "Conch_smallDescription", "%s is now the chief and cannot be designated this round." },

                        { "CrystalBall_name", "Crystal Ball" },
                        { "CrystalBall_description",
                                        "Allows to vote after all players, at each vote.\nPermanent use. Card discarded after the death of its owner." },
                        { "CrystalBall_smallDescription",
                                        "%s will now vote after the rest of the crew and see their votes before voting." },

                        { "FishingRod_name", "Fishing Rod" },
                        { "FishingRod_description",
                                        "Allows to get more food from fishing action.\nPermanent use. Card discarded after the death of its owner." },
                        { "FishingRod_smallDescription", "%s will now get more food during the food action." },

                        { "Flashlight_name", "Flashlight" },
                        { "Flashlight_description", "Allows to see the next three cards in the deck." },
                        { "Flashlight_smallDescription", "%s has seen the next three cards in the deck." },

                        { "GiftBasket_name", "Gift Basket" },
                        { "GiftBasket_description",
                                        "If there is a lack of food or water, this object provides the missing rations, so that nobody is sacrificed. Cannot be used to leave the island." },
                        { "GiftBasket_smallDescription", "%s protects the crew from a shortage of resources." },

                        { "Gourd_name", "Gourd" },
                        { "Gourd_description",
                                        "Allows to get twice more water rations during the water action.\nPermanent use. Card discarded after the death of its owner." },
                        { "Gourd_smallDescription", "%s will now get twice more water during the water action." },

                        { "Gun_name", "Gun" },
                        { "Gun_description",
                                        "Kills one other player if a cartridge is available.\nPermanent use. Card not discarded after the death of its owner." },
                        { "Gun_smallDescription", "%s tries to kill %s!" },

                        { "KitBBQCannibal_name", "Kit BBQ Cannibal" },
                        { "KitBBQCannibal_description",
                                        "Provides two food rations for each player dead during this round." },
                        { "KitBBQCannibal_smallDescription",
                                        "%s makes two rations of food for each player dead this round, which means %d food rations." },

                        { "LuxuryCarKey_name", "Luxury Car Key" },
                        { "LuxuryCarKey_description", "This object is useless, not even to brag." },

                        { "Matches_name", "Matches" },
                        { "Matches_description",
                                        "Allows to use a stagnant water or a rotten fish without being sick, for this round." },
                        { "Matches_smallDescription", "%s makes possible to it stale food without getting sick." },

                        { "MetalSheet_name", "Metal Sheet" },
                        { "MetalSheet_description", "Protects from one gunshot." },
                        { "MetalSheet_smallDescription", "%s is protected from a gunshot!" },

                        { "OldBrief_name", "Old Brief" },
                        { "OldBrief_description",
                                        "This object is useless, although it may possibly provide some comfort." },

                        { "Pendulum_name", "Pendulum" },
                        { "Pendulum_description",
                                        "Allows to impose one action to another player (food, water, wood or card)." },
                        { "Pendulum_smallDescription", "%s imposes the action %s to %s." },

                        { "RottenFish_name", "Rotten Fish" },
                        { "RottenFish_description",
                                        "Is equivalent to one food ration but makes the player sick for one round." },
                        { "RottenFish_smallDescription", "%s gets one food ration but is now sick." },

                        { "Sandwich_name", "Sandwich" },
                        { "Sandwich_description", "Is equivalent to one food ration." },
                        { "Sandwich_smallDescription", "%s earns one food ration." },

                        { "Sardines_name", "Sardines" },
                        { "Sardines_description", "Is equivalent to three food rations." },
                        { "Sardines_smallDescription", "%s earns three food rations." },

                        { "SleepingPills_name", "Sleeping Pills" },
                        { "SleepingPills_description", "Allows to rob a random card to up to three other players." },
                        { "SleepingPills_smallDescription", "%s robs cards from other players !" },

                        { "Spyglass_name", "Spyglass" },
                        { "Spyglass_description", "Allows to see the cards of the other players." },
                        { "Spyglass_smallDescription", "%s has seen the cards of each player." },

                        { "StagnantWater_name", "Stagnant Water" },
                        { "StagnantWater_description",
                                        "Is equivalent to one water ration but makes the player sick for one round." },
                        { "StagnantWater_smallDescription", "%s gets one water ration but is now sick." },

                        { "ToiletBrush_name", "Toilet Brush" },
                        { "ToiletBrush_description", "This object is useless, at least on this island." },

                        { "VegetableMill_name", "Vegetable Mill" },
                        { "VegetableMill_description", "Converts two food rations in two water rations." },
                        { "VegetableMill_smallDescription",
                                        "%s has converted two food rations into two water rations." },

                        { "VoodooDoll_name", "Voodoo Doll" },
                        { "VoodooDoll_description", "Allows to resuscitate a dead player at a round beginning." },
                        { "VoodooDoll_smallDescription", "%s resurrects %s." },

                        { "WaterBottle_name", "Water Bottle" },
                        { "WaterBottle_description", "Is equivalent to one water ration." },
                        { "WaterBottle_smallDescription", "%s earns one water ration." },

                        { "WinningLotteryTicket_name", "Winning Lottery Ticket" },
                        { "WinningLotteryTicket_description",
                                        "This object is useless (but you could have bought a boat with it)." },

                        { "WoodenPlank_name", "Wooden Plank" },
                        { "WoodenPlank_description", "Adds one plank on the raft." },
                        { "WoodenPlank_smallDescription", "%s earns one wooden plank." },

                        { "Buoy_name", "Buoy" },
                        { "Buoy_description",
                                        "Allows the user to pick two cards and to discard one while keeping the other, during the card action.\nPermanent use. Card not discarded after the death of its owner." },
                        { "Buoy_smallDescription",
                                        "%s will now be able to choose between two cards during the card action." },

                        { "Cat_name", "Cat" },
                        { "Cat_description", "Allows the user to get two food rations, when resources are lacking." },
                        { "Cat_smallDescription", "%s sacrifices the cat to get two food rations." },

                        { "ChineseNoodles_name", "Chinese Noodles" },
                        { "ChineseNoodles_description", "Converts two water rations in two food rations." },
                        { "ChineseNoodles_smallDescription",
                                        "%s has converted two water rations in two food rations." },

                        { "ConcaveMetalSheet_name", "Concave Metal Sheet" },
                        { "ConcaveMetalSheet_description", "Deviates a gunshot to an other close player." },
                        { "ConcaveMetalSheet_smallDescription", "%s has deviated a bullet on an other player !" },

                        { "Crate_name", "Crate" }, { "Crate_description", "Gives two wood fragments." },
                        { "Crate_smallDescription", "%s has earned two wood fragments." },

                        { "ExpandingBullet_name", "Expanding Bullet" },
                        { "ExpandingBullet_description",
                                        "To be associated with the gun. Goes through any metal sheet. Discarded after utilization." },
                        { "ExpandingBullet_smallDescription", "%s has shot someone to death." },

                        { "FishBowl_name", "Fish Bowl" },
                        { "FishBowl_description",
                                        "Allows the user to get two water rations, when resources are lacking." },
                        { "FishBowl_smallDescription", "%s sacrifices the fish to get two water rations." },

                        { "Hone_name", "Hone" },
                        { "Hone_description",
                                        "If associated with an axe, allows to kill on the closest player. The axe is then discarded." },
                        { "Hone_smallDescription", "%s murdered %s with an axe !" },

                        { "KitShaman_name", "Kit Shaman" },
                        { "KitShaman_description", "If the weather is 0, allows to change it to 1." },
                        { "KitShaman_smallDescription", "%s uses shamanic powers to change the weather." },

                        { "Magazine_name", "Magazine" },
                        { "Magazine_description", "This thing will not help you to leave this island." },

                        { "MetalDetector_name", "Metal Detector" },
                        { "MetalDetector_description",
                                        "Allows to get cartridges and metal sheet from the four next cards in the deck but discard the other cards." },
                        { "MetalDetector_smallDescription",
                                        "%s has got %d cartridges and/or metal sheet from the deck." },

                        { "Rum_name", "Rum" },
                        { "Rum_description",
                                        "Starting from the chief, each player gives one card to the next player in the round order." },
                        { "Rum_smallDescription",
                                        "Each player has to give one card to the next player in the round order." },

                        { "Taser_name", "Taser" },
                        { "Taser_description", "Allows to rob one revealed card from another player." },
                        { "Taser_smallDescription", "%s robbed the card %s from %s." },

                        { "Whip_name", "Whip" },
                        { "Whip_description", "Makes another player to be bitten if the wood action fails." },
                        { "Whip_smallDescription", "%s makes %s to pick some wooden fragments." },

                        { "player_name", "Player" }, { "yesNoQuestion", "(y/n)" },
                        { "wouldLikePlayCard?", "Would like to play a card ?" },
                        { "cardsDisplay", "Your cards are : " }, { "invalidChoice", "Invalid choice !" },
                        { "chooseACard", "Choose a card with its index" }, { "choiceResult", "You choose " },
                        { "cannotPlayCard", "You cannot play this card now !" },
                        { "choosePlayerList", "Choose a player in this list : " },
                        { "chooseActionList", "Choose an action in this list : " },
                        { "howManyFragments", "How many plank fragments would you like to get ?" },
                        { "gotFood", "Food rations acquired : " }, { "gotWater", "Water rations acquired : " },
                        { "gotWood", "Plank fragments acquired : " }, { "gotCard", " got a card" },
                        { "playerGotSick", " got sick !" },
                        { "decideWhoDie", "Someone has to die. Choose one player." },
                        { "decideWhoVote", "Someone has to die. Vote for one player." }, { "votesFor", " votes for " },
                        { "nextWeather", "The next two weather : " },
                        { "cardsOfAllPlayers", "The cards of all players : " },
                        { "nextCards", "The next cards in the deck : " },
                        { "sickNowCuredPlayer", " was sick and could not play, now cured" },
                        { "playerTurn", "'s turn !" }, { "isSickPlayer", " is sick and cannot play !" },
                        { "isDeadPlayer", " is dead and cannot play !" },
                        { "distributionTime", "It is time to distribute the rations of the round !" },
                        { "distributionEnd", "Distribution ended !" }, { "allPlayersDead", "All players are dead !" },
                        { "hurricane", "Hurricane !" }, { "voluntaryDeparture", "Voluntary Departure !" },
                        { "newRoundInit", "It is a new day !" }, { "endGame", "End of the game!" },
                        { "foodAction", " is getting food !" }, { "waterAction", " is getting water !" },
                        { "woodAction", " is getting wood !" }, { "cardAction", " is getting a card !" },
                        { "playAgain", " will play again !" },
                        { "votingTwice", " will be voting twice, thanks to the club" },
                        { "designatedPlayer", " is designated by the crew" },
                        { "chiefWillDecide", "The chief will decide who will be sacrificed" },
                        { "sacrificedForCrew", " has been sacrificed for the sake of the crew :(" },
                        { "cardsOfPlayersSpyglass", "The cards of the players : " }, { "hasCard", " has the cards " },
                        { "chooseAction", "Choose your action" }, { "notification", "Notification !" },
                        { "choosePlayer", "Choose a player" }, { "chooseNbTries", "Choose your number of tries" },
                        { "cardDescription", "Card Description" }, { "food", "Food" }, { "water", "Water" },
                        { "wood", "Wood" }, { "card", "Card" }, { "next", "Next" }, { "beginVote", "Begin Vote" },
                        { "confirmPlayer", "Confirm Designated Player" }, { "useCard", "Use the card" },
                        { "hiddenCards", "Hidden Cards" }, { "revealedCards", "Revealed Cards" },
                        { "woodPlankFragments", "Wood Plank Fragments" }, { "woodPlanks", "Wood Planks" },
                        { "weather", "Weather" }, { "alive", "Alive" }, { "round", "Round" },
                        { "currentPlayer", "Current Player" }, { "newChief", " is the new chief !" },
                        { "startButton", "Start" }, { "defaultName", "Player" }, { "yourNameLabel", "Your name : " },
                        { "nbPlayerLabel", "Number of players : " }, { "difficulty", "Difficulty : " },
                        { "difficultyEasy", "Easy" }, { "difficultyMedium", "Medium" }, { "difficultyHard", "Hard" },
                        { "typesPersonalities", "There %s %d %s player%s !" },
                        { "personalityUpdate", "%s has updated their personality !" },
                        { "useExpansionBox", "Use the expansion" },
                        { "rumDistributionEvent", "%s is giving a %s to %s." } };
}