package songming.straing.model;

/**
 * 聊天bean
 */
public class ChatInfo {
    public String avatar;
    public String content;
    public long userid;

    public ChatInfo(String avatar, String content,long userid) {
        this.avatar = avatar;
        this.content = content;
        this.userid=userid;
    }
}
