package org.cloud.sonic.controller.tools.robot;

import org.cloud.sonic.common.exception.SonicException;
import org.cloud.sonic.controller.models.interfaces.RobotType;
import org.cloud.sonic.controller.tools.robot.vendor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * @author ayumi760405
 * @Date 2022/12/19
 * @Des 机器人推送工厂，用来取得机器人推送实作
 */

@Component
public class RobotFactory {

    @Autowired
    private ApplicationContext context;

    /**
     * @param robotType  机器人种类
     * @author ayumi760405
     * @des 取得推送机器人实作
     * @date 2022/12/20 18:20
     */
    public RobotMessenger getRobotMessenger(int robotType) {
        RobotMessenger robotMessenger;
        switch (robotType) {
            case RobotType.DingTalk -> robotMessenger = context.getBean(DingTalkImpl.class);
            case RobotType.WeChat -> robotMessenger = context.getBean(WeChatImpl.class);
            case RobotType.FeiShu -> robotMessenger = context.getBean(FeiShuImpl.class);
            case RobotType.YouSpace -> robotMessenger = context.getBean(YouSpaceImpl.class);
            case RobotType.Telegram -> robotMessenger = context.getBean(TelegramImpl.class);
            case RobotType.LineNotify -> robotMessenger = context.getBean(LineNotifyImpl.class);
            default -> throw new SonicException("Unsupported robot type");
        }
        return robotMessenger;
    }
}
