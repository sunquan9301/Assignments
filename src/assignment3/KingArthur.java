package assignment3;

/**
 * KingArthur
 */
public class KingArthur extends Thread {

    /**
     *  The different behavior
     *  1.Entering greate hall :
     *  the condition of it is there are none knight with new quest in Greate Hall
     *  2.Start meeting
     *  the condition of it is all knight in Greate Hall have sitted down
     *  3.Ending meeting
     *  the condition of it is all knight in Greate Hall have standed up
     */
    enum Behavior {
        ENTERING_GREAT_HALL,
        START_MEETING,
        ENDING_MEETING_AND_LEAVE,
    }

    private Agenda agenda;
    private Behavior behavior;

    //constructor
    public KingArthur(Agenda agenda) {
        behavior = Behavior.ENTERING_GREAT_HALL;
        this.agenda = agenda;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //loop to execute command
                executeCommand();
            } catch (Exception e) {
                this.interrupt();
            }
        }
    }

    /**
     * entering greate hall -> start meeting -> ending meeting
     * @throws Exception
     */
    public void executeCommand() throws Exception {
        switch (behavior) {
            case ENTERING_GREAT_HALL:
                Thread.sleep(Params.getMinglingTime());
                agenda.kingArthurEnterGreatHall();
                behavior = Behavior.START_MEETING;
                break;
            case START_MEETING:
                Thread.sleep(Params.getMinglingTime());
                agenda.startMeeting();
                behavior = Behavior.ENDING_MEETING_AND_LEAVE;
                break;
            case ENDING_MEETING_AND_LEAVE:
                Thread.sleep(Params.getMinglingTime());
                agenda.kingArthurEndMeetingAndLeave();
                behavior = Behavior.ENTERING_GREAT_HALL;
                break;
        }
    }
}
