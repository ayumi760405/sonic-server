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
package org.cloud.sonic.controller.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.cloud.sonic.common.config.WebAspect;
import org.cloud.sonic.common.http.RespEnum;
import org.cloud.sonic.common.http.RespModel;
import org.cloud.sonic.controller.models.base.TypeConverter;
import org.cloud.sonic.controller.models.domain.Agents;
import org.cloud.sonic.controller.models.dto.AgentsDTO;
import org.cloud.sonic.controller.services.AgentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.cloud.sonic.controller.transport.TransportWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhouYiXun
 * @des
 * @date 2021/8/28 21:49
 */
@Api(tags = "Agent端相关")
@RestController
@RequestMapping("/agents")
public class AgentsController {

    @Autowired
    private AgentsService agentsService;

    @WebAspect
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "id", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "position", value = "position", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "type", value = "type", dataTypeClass = String.class)
    })
    @GetMapping("/hubControl")
    public RespModel<?> hubControl(@RequestParam(name = "id") int id, @RequestParam(name = "position") int position,
                                   @RequestParam(name = "type") String type) {
        JSONObject result = new JSONObject();
        result.put("msg", "hub");
        result.put("position", position);
        result.put("type", type);
        TransportWorker.send(id, result);
        return new RespModel<>(RespEnum.HANDLE_OK);
    }

    @WebAspect
    @ApiOperation(value = "查询所有Agent端", notes = "获取所有Agent端以及详细信息")
    @GetMapping("/list")
    public RespModel<List<AgentsDTO>> findAgents() {
        return new RespModel<>(
                RespEnum.SEARCH_OK,
                agentsService.findAgents().stream().map(TypeConverter::convertTo).collect(Collectors.toList())
        );
    }

    @WebAspect
    @ApiOperation(value = "修改agent信息", notes = "修改agent信息")
    @PutMapping("/update")
    public RespModel<String> update(@RequestBody JSONObject jsonObject) {
        agentsService.update(jsonObject.getInteger("id"),
                jsonObject.getString("name"), jsonObject.getInteger("highTemp"),
                jsonObject.getInteger("highTempTime"), jsonObject.getInteger("robotType"),
                jsonObject.getString("robotToken"), jsonObject.getString("robotSecret"));
        return new RespModel<>(RespEnum.HANDLE_OK);
    }

    @WebAspect
    @ApiOperation(value = "查询Agent端信息", notes = "获取对应id的Agent信息")
    @GetMapping
    public RespModel<?> findOne(@RequestParam(name = "id") int id) {
        Agents agents = agentsService.findById(id);
        if (agents != null) {
            return new RespModel<>(RespEnum.SEARCH_OK, agents);
        } else {
            return new RespModel<>(RespEnum.ID_NOT_FOUND);
        }
    }
}
