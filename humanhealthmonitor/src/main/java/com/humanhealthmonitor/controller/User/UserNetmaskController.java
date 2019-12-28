package com.humanhealthmonitor.controller.User;

import com.humanhealthmonitor.MsgQueue;
import com.humanhealthmonitor.SocketTask;
import com.humanhealthmonitor.pojo.Netmask;
import com.humanhealthmonitor.pojo.User;
import com.humanhealthmonitor.service.UserNetmaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class UserNetmaskController {

    @Autowired
    private UserNetmaskService userNetmaskService;

    @RequestMapping("/monitorCenter/netmaskRelated")
    public String netmaskRelated(HttpServletRequest request) throws InterruptedException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);
        System.out.println("<user>:"+user.getUserId());


        ArrayList<Netmask> netmaskArrayList = userNetmaskService.queryAllNetmask();
        ArrayList<Netmask> netmaskArrayListOnline = new ArrayList<>();
        Netmask netmaskConnected = new Netmask();
        ArrayList<Integer> socketOnlineNumber = new ArrayList<>();
        for(SocketTask st: MsgQueue.socketTasks) {
            if(st != null && st.getSocket() != null) {
                socketOnlineNumber.add(st.getTaskNum());
            }
        }

        for(int i = 0;i<netmaskArrayList.size();++i) {
            for(Integer so: socketOnlineNumber) {
                if(netmaskArrayList.get(i).getId() == so) {
                    netmaskArrayListOnline.add(netmaskArrayList.get(i));
                }
            }
        }


        for (Netmask nm: netmaskArrayListOnline) {
            System.out.println("nm.get..."+nm.getNetmask_name()+nm.getRelated_user_id());
            if(user.getUserId().equals(nm.getRelated_user_id())) {
                netmaskConnected = nm;
                //System.out.println(nm.getNetmask_name());
            }
        }

        for(int i=netmaskArrayListOnline.size()-1;i>=0;i--){
            if(netmaskArrayListOnline.get(i)!=null&&(netmaskArrayListOnline.get(i).getRelated_user_id()!=null)) {
                netmaskArrayListOnline.remove(i);
                System.out.println("remove" + i);
            }
        }


        boolean netmaskConnectedIsNull = true;
        if(netmaskConnected.getId() == 0) {
            netmaskConnectedIsNull =false;
        }

        System.out.println("netmaskConnected"+netmaskConnected.getId()+netmaskConnected.getNetmask_name() + (netmaskConnectedIsNull));

        request.setAttribute("netmaskListOnline", netmaskArrayListOnline);
        request.setAttribute("netmaskConnected", netmaskConnected);
        request.setAttribute("netmaskConnectedIsNull", netmaskConnectedIsNull);
        request.setAttribute("a", "123");

        return "monitorCenter/netmaskRelated";
    }


}
