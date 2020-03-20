package assignment3;

/**
 * Knight
 */
public class Knight extends Thread {
    //the differe behavior of Knight;
    enum Behavior {
        ACQUIREING_QUEST,
        LEAVING_GREATE_HALL,
        COMPLETING_QUEST,
        ENTERING_GREATE_HALL,
        SITTING_DOWN,
        REPORTING_QUES,
    }

    //The agenda to process all releate behavior
    private Agenda agenda;
    //questId
    int questionId = -1;
    //nameId
    int nameId = -1;
    private Behavior behavior;

    public Knight(int id, Agenda agenda) {
        this.nameId = id;
        this.agenda = agenda;
        if (id == 0) {
            //init of all system the knight 1are acquired with question 1;
            behavior = Behavior.COMPLETING_QUEST;
            questionId = 1;
        } else {
            //for other knights except Knight1, the init behavior is entering greate hall
            behavior = Behavior.ENTERING_GREATE_HALL;
        }
    }

    @Override
    public void run() {
        try {
            //a interval to start execute command
            sleep(nameId * 50);
            while (true) {
                //loop to execute different command to process the behavior
                executeCommand();
            }
        } catch (Exception e) {
            this.interrupt();
        }
    }

    /**
     * mingling -> entering greate hall -> discuss adventure time with other -> sitting down
     * -> disscuss adventure -> report ques -> acquire quest -> stand up -> discuss adventure -> leaving
     * -> mingling -> complete quest.
     * @throws Exception
     */
    public void executeCommand() throws Exception {
//        System.out.println("Knight"+(nameId+1)+"executeCommand ====== behavior = " + behavior);
        switch (behavior) {
            case ACQUIREING_QUEST:
                this.questionId = agenda.acquireQuest(this);
                System.out.println("Knight " + (nameId + 1) + " acquires Quest " + questionId);
                Thread.sleep(Params.getKingAcquireQuestTime());
                agenda.standUp(this);
                System.out.println("Knight " + (nameId + 1) + " stands from the Round Table.");
                behavior = Behavior.LEAVING_GREATE_HALL;
                break;
            case LEAVING_GREATE_HALL:
                Thread.sleep(Params.getKingDiscussAdventureTime());
                agenda.prepareLeaveGreateHall(this);
                behavior = Behavior.COMPLETING_QUEST;
                break;
            case COMPLETING_QUEST:
                Thread.sleep(Params.getMinglingTime());
                System.out.println("Knight " + (nameId + 1) + " sets of to complete Quest " + questionId + "!");
                Thread.sleep(Params.getKingCompleteQuestTime());
                agenda.completeQuest(this);
                System.out.println("Knight " + (nameId + 1) + " completes Quest " + questionId + "!");
                behavior = Behavior.ENTERING_GREATE_HALL;
                break;
            case ENTERING_GREATE_HALL:
                Thread.sleep(Params.getMinglingTime());
                agenda.prepareEnderGreateHall(this);
                behavior = Behavior.SITTING_DOWN;
                break;
            case SITTING_DOWN:
                Thread.sleep(Params.getKingDiscussAdventureTime());
                agenda.sittingDown(this);
                behavior = Behavior.REPORTING_QUES;
                break;
            case REPORTING_QUES:
                Thread.sleep(Params.getMinglingTime());
                if (questionId > 0) {
                    agenda.reportQuest(this);
                    Thread.sleep(Params.getMinglingTime());
                }
                behavior = Behavior.ACQUIREING_QUEST;
                break;

        }
    }
}
