package listeners;

import commands.etc.BotStats;
import commands.etc.CmdLog;
import core.Main;
import core.SSSS;
import core.coreCommands;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.STATICS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class botListener extends ListenerAdapter{


    private void addToLogfile(MessageReceivedEvent e) throws IOException {

        File logFile = new File("CMDLOG.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true));

        if (!logFile.exists())
            logFile.createNewFile();

        bw.write(String.format("%s [%s (%s)] [%s (%s)] '%s'\n",
                coreCommands.getCurrentSystemTime(),
                e.getGuild().getName(),
                e.getGuild().getId(),
                e.getAuthor().getName(),
                e.getAuthor().getId(),
                e.getMessage().getContent()));
        bw.close();

    }


    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        BotStats.messagesProcessed++;

        if (e.getChannelType().equals(ChannelType.PRIVATE)) return;

        if (e.getMessage().getContent().startsWith(SSSS.getPREFIX(e.getGuild())) && e.getMessage().getAuthor().getId() != e.getJDA().getSelfUser().getId()) {
            if (!commands.guildAdministration.Blacklist.check(e.getAuthor(), e.getGuild())) return;
            try {
                Main.handleCommand(Main.parser.parse(e.getMessage().getContent(), e));
                if (STATICS.commandConsoleOutout)
                    System.out.println(coreCommands.getCurrentSystemTime() + " [Info] [Commands]: Command '" + e.getMessage().getContent() + "' was executed by '" + e.getAuthor() + "' (" + e.getGuild().getName() + ")!");
                ArrayList<String> list = new ArrayList<>();
                list.add(e.getGuild().getId());
                list.add(coreCommands.getCurrentSystemTime());
                list.add(e.getMember().getEffectiveName());
                list.add(e.getMessage().getContent());
                STATICS.cmdLog.add(list);
                addToLogfile(e);
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}

