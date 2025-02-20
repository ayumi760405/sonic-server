/*
 *   sonic-server  Sonic Cloud Real Machine Platform.
 *   Copyright (C) 2022 SonicCloudOrg
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as published
 *   by the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.cloud.sonic.controller.tools;

import org.cloud.sonic.controller.tools.robot.RobotFactory;
import org.cloud.sonic.controller.tools.robot.RobotMessenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * @author ZhouYiXun
 * @des 机器人推送相关工具类，可以参考 https://developers.dingtalk.com/document/app/push-robots
 * @date 2021/8/15 18:20
 */
@Component
public class RobotMsgTool {
    private final Logger logger = LoggerFactory.getLogger(RobotMsgTool.class);

    @Autowired
    private RobotFactory robotFactory;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @param token     机器人token
     * @param secret    机器人密钥
     * @param suiteName 套件名称
     * @param pass      通过数量
     * @param warn      警告数量
     * @param fail      失败数量
     * @param projectId 项目id
     * @param resultId  结果id
     * @return void
     * @author ZhouYiXun
     * @des 发送每次测试结果到钉钉
     * @date 2021/8/20 18:29
     */
    public void sendResultFinishReport(String token, String secret, String suiteName, int pass,
                                       int warn, int fail, int projectId, int resultId, int type) {
        RobotMessenger robotMessenger = robotFactory.getRobotMessenger(type);
        robotMessenger.sendResultFinishReport(restTemplate, token, secret, suiteName, pass, warn, fail, projectId, resultId);
    }

    /**
     * @param token    机器人token
     * @param secret   机器人密钥
     * @param platform 平台
     * @param version  版本号
     * @param url      安装包链接
     * @param detail   具体安装情况
     * @return void
     * @author ZhouYiXun
     * @des 发送装包完毕通知
     * @date 2021/8/20 18:33
     */
//    public void sendInstallPackageFinishReport(String token, String secret, String platform,
//                                               String version, String url, JSONArray detail) {
//        JSONObject jsonObject = new JSONObject();
//        JSONObject markdown = new JSONObject();
//        String device = "";
//        //遍历详情里面的结果，组装成多条安装结果的markdown
//        for (Object o : detail) {
//            JSONObject deviceDetail = (JSONObject) o;
//            String statusColor;
//            if (deviceDetail.getString("status").equals("PASS")) {
//                statusColor = "<font color=#67C23A>PASS</font>";
//            } else {
//                statusColor = "<font color=#F56C6C>FAIL</font>";
//            }
//            device += ("> ###### " + deviceDetail.getString("name") + "  ---  " + statusColor + " \n");
//        }
//        markdown.put("text", "#### **Sonic装包完成通知** \n" +
//                "  ###### 平台：" + platform + " \n" +
//                "  ###### 版本号：" + (version.length() == 0 ? "未知版本" : version) + " \n" +
//                device +
//                "  ###### 安装地址：[点击查看](" + url + ")");
//        markdown.put("title", "Sonic装包完成通知");
//        jsonObject.put("msgtype", "markdown");
//        jsonObject.put("markdown", markdown);
//        signAndSend(token, secret, jsonObject);
//    }

    public void sendDayReportMessage(String token, String secret, int projectId, String projectName,
                                     String yesterday, String today, int passCount, int warnCount, int failCount, int type) {
        RobotMessenger robotMessenger = robotFactory.getRobotMessenger(type);
        robotMessenger.sendDayReportMessage(restTemplate, token, secret, projectId, projectName, yesterday, today, passCount, warnCount, failCount);
    }

    public void sendErrorDevice(String token, String secret, int type, int errorType, int tem, String udId) {
        RobotMessenger robotMessenger = robotFactory.getRobotMessenger(type);
        robotMessenger.sendErrorDevice(restTemplate, token, secret, errorType, tem, udId);
    }

    public void sendWeekReportMessage(String token, String secret, int projectId, String projectName,
                                      String yesterday, String today, int passCount, int warnCount, int failCount, int count, int type) {
        RobotMessenger robotMessenger = robotFactory.getRobotMessenger(type);
        robotMessenger.sendWeekReportMessage(restTemplate, token, secret, projectId, projectName, yesterday, today, passCount, warnCount, failCount, count);
    }
}
