package songming.straing.model;

import java.io.Serializable;
import java.util.List;

/**
 * 任务详情
 */
public class MissionInfo implements Serializable {


    /**
     * trainingID : 9
     * userID : 10000000
     * type : 1
     * beginAt : 1461470371000
     * finishAt : 1461507554000
     * presetCount : 80
     * presetGroup : 8
     * perBreakTime : 30
     * location : 1
     * drinking : 1
     * gear : 1
     * actualCount : 80
     * actualGroup : 10
     * actualBreakTime : 300
     * actualConsumTime : 500
     * status : 3
     * createAt : 1461507067000
     * content : 运动预设目标:
     * 在 8 内完成 80 个 伏地挺身;
     * 选择 室外/操场 环境，计划平均休息 30 秒;
     * 选择使用 NIKE 护具和 肌肉科技 作为能量补充;
     * <p/>
     * title : 伏地挺身 #8G-640
     * isSuccess : 1
     */

    public int trainingID;
    public int userID;
    public int type;
    public long beginAt;
    public long finishAt;
    public int presetCount;
    public int presetGroup;
    public int perBreakTime;
    public int location;
    public int drinking;
    public int gear;
    public int actualCount;
    public int actualGroup;
    public int actualBreakTime;
    public int actualConsumTime;
    public int status;
    public long createAt;
    public String content;
    public String title;
    public int isSuccess;
    public List<MissionProgress> items;

    public static class MissionProgress {
        public int count;
        public int breakTime;
    }
}
