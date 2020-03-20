package assignment3;

/**
 * produce quest in quest queue
 */
public class QuestProducer extends Thread {
    private Agenda agenda;

    public QuestProducer(Agenda dataManager) {
        this.agenda = dataManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                agenda.produceQuest();
                Thread.sleep(Params.getProduceQuestTime());
            } catch (Exception e) {
                this.interrupt();
            }
        }
    }
}
