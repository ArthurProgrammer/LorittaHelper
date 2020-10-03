package net.perfectdreams.loritta.helper.listeners

import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.perfectdreams.loritta.helper.LorittaHelper
import net.perfectdreams.loritta.helper.serverresponses.EnglishResponses
import net.perfectdreams.loritta.helper.serverresponses.PortugueseResponses
import net.perfectdreams.loritta.helper.utils.Constants
import net.perfectdreams.loritta.helper.utils.dontmentionstaff.EnglishDontMentionStaff
import net.perfectdreams.loritta.helper.utils.dontmentionstaff.PortugueseDontMentionStaff

class MessageListener(val m: LorittaHelper) : ListenerAdapter() {
    private val dontMentionStaffs = listOf(
        EnglishDontMentionStaff(),
        PortugueseDontMentionStaff()
    )

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        super.onGuildMessageReceived(event)

        // If this check wasn't here, Loritta Helper will reply to a user... then she thinks that it is someone asking
        // something, and the loop goes on...
        if (event.author.isBot)
            return

        // We launch in a separate task because we want both responses (automatic responses + don't mention staff) to go off, if they
        // are triggered in the same message
        m.launch {
            dontMentionStaffs.forEach {
                it.onMessageReceived(event)
            }
        }

        m.launch {
            val channelResponses = when (event.message.channel.idLong) {
                Constants.PORTUGUESE_SUPPORT_CHANNEL_ID -> {
                    PortugueseResponses.responses
                }
                Constants.ENGLISH_SUPPORT_CHANNEL_ID -> {
                    EnglishResponses.responses
                }
                else -> null
            }

            if (channelResponses != null) {
                val responses = channelResponses.sortedByDescending { it.priority }
                    .firstOrNull { it.handleResponse(event, event.message.contentRaw) }?.getResponse(event, event.message.contentRaw) ?: return@launch

                if (responses.isNotEmpty())
                    event.channel.sendMessage(
                        MessageBuilder()
                            // We mention roles in some of the messages, so we don't want the mention to actually go off!
                            .setAllowedMentions(listOf(Message.MentionType.USER, Message.MentionType.CHANNEL, Message.MentionType.EMOTE))
                            .setContent(responses.joinToString("\n") { it.build(event) })
                            .build()
                    ).queue()
                return@launch
            }
        }
    }
}