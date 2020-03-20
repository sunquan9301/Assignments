package assignment3;

public class Main {
    public static void main(String[] args) throws Exception{
        Agenda agenda = new Agenda();
        QuestProducer questProducer = new QuestProducer(agenda);
        questProducer.start();
        Thread.sleep(Params.getProduceQuestTime()*2);
        for (int i = 0; i < Params.KNIGHT_COUNT; i++) {
            Knight knight = new Knight(i, agenda);
            knight.start();
        }
        KingArthur kingArthur = new KingArthur(agenda);
        kingArthur.start();
    }
}
