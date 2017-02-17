package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

public class EightBall implements Command {

    public static String HELP = ":warning:  USAGE: ` ~8ball <question> to ask the magic 8ball a special question!`";

    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    public void action(String[] args, MessageReceivedEvent event) {

        String[] answers = {
                "Absolutly!",
                "Yes, sure.",
                "For sure, dude!",
                "Maybe...",
                "Nah...",
                "No, really, no..."
        };

        Integer randInt = new Random().nextInt(6);

        String msg = "";
        for (String a : args) {
            msg += a + " ";
        }

        event.getTextChannel().sendMessage(
                "Question: " + msg + "\n" +
                "Answer: " + answers[randInt]
        ).queue();


    }

    public void executed(boolean success, MessageReceivedEvent event) {

    }

    public String help() {
        return HELP;
    }
}
