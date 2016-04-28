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
     * finishAt : 0
     * presetCount : 80
     * presetGroup : 8
     * perBreakTime : 30
     * location : 1
     * drinking : 1
     * gear : 1
     * actualCount : 0
     * actualGroup : 0
     * actualBreakTime : 0
     * actualConsumTime : 0
     * status : 1
     * createAt : 1461507067000
     * content : 运动目标:
     * 在 8 组内完成 80 个 伏地挺身;
     * 由于你在 室外/操场 环境中，并平均休息 30 秒;
     * 同时你选择使用 NIKE 护具和 肌肉科技 作为能量补充;
     * 根据你实际的完成结果来最终评定你的得分
     * title : 伏地挺身 #8G-80
     * isSuccess : 0
     * items : []
     *
     * training.status 状态：1.创建 2.开始 3.结束 training.isSuccess 0:未达成目标 1:达成
     */

    public int trainingID;
    public int userID;
    public int type;
    public long beginAt;
    public int finishAt;
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

    public static class MissionProgress{
        public int count;
        public int breakTime;
    }
}
