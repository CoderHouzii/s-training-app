package songming.straing.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import songming.straing.R;
import songming.straing.app.eventbus.Events;
import songming.straing.app.https.base.BaseResponse;
import songming.straing.app.https.request.MissionCreateRequest;
import songming.straing.app.https.request.MissionPreLoadRequest;
import songming.straing.model.MissionInfo;
import songming.straing.model.MissionLoadInfo;
import songming.straing.ui.fragment.base.BaseFragment;
import songming.straing.utils.TimeUtils;
import songming.straing.utils.ToastUtils;
import songming.straing.utils.UIHelper;

/**
 * 创建任务
 */
public class MissionFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQ_PRE_LOAD=0x10;
    private static final int REQ_PRE_CREATE=0x11;

    private boolean isInitialize=true;

    private ViewHolder vh;
    private MissionPreLoadRequest mPreLoadRequest;
    private MissionCreateRequest mCreateRequest;

    private Map<String,Integer> typeMap;
    private Map<String,Integer> locationMap;
    private Map<String,Integer> drinkMap;
    private Map<String,Integer> gearsMap;

    private boolean isStart=false;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_mission, container, false);
    }

    @Override
    public void bindData() {
        vh=new ViewHolder(mView);

        typeMap=new HashMap<>();
        locationMap=new HashMap<>();
        drinkMap=new HashMap<>();
        gearsMap=new HashMap<>();

        vh.pinner_start_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isInitialize) {
                    String time = parent.getSelectedItem().toString().trim();
                    if (!TimeUtils.compareTimeWithHM(time)) {
                        ToastUtils.ToastMessage(mContext, "您选择的时间已经过了哟，请重新选择");
                    }
                }
                isInitialize=false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vh.create.setOnClickListener(this);
        vh.start.setOnClickListener(this);
        initReq();

    }

    private void initReq() {
        mPreLoadRequest=new MissionPreLoadRequest();
        mPreLoadRequest.setRequestType(REQ_PRE_LOAD);
        mPreLoadRequest.setOnResponseListener(this);
        mPreLoadRequest.execute();

        mCreateRequest=new MissionCreateRequest();
        mCreateRequest.setRequestType(REQ_PRE_CREATE);
        mCreateRequest.setOnResponseListener(this);

    }

    @Override
    public void onSuccess(BaseResponse response) {
        super.onSuccess(response);
        if (response.getStatus()==1){
            switch (response.getRequestType()){
                case REQ_PRE_LOAD:
                    MissionLoadInfo info= (MissionLoadInfo) response.getData();
                    updateData(info);
                    break;
                case REQ_PRE_CREATE:
                    if (!isStart) {
                        EventBus.getDefault().post(new Events.RefreshMissionDetail());
                        EventBus.getDefault().post(new Events.ChangeToFragment(Events.ChangeToFragment.FRAG_INDEX));
                    }else {
                        MissionInfo info2= (MissionInfo) response.getData();
                        EventBus.getDefault().post(new Events.RefreshMissionDetail());
                        UIHelper.startToMissionDetailActivity(mContext,info2.trainingID);
                    }
                    break;
            }
        }
    }

    private void updateData(MissionLoadInfo info) {
        if (info==null)return;

        // type的adapter
        if (vh.pinner_type.getAdapter()==null){
            saveMapRev(info.types,typeMap);
            String[] data= map2StringArray(info.types);
            ArrayAdapter typeAdapter=new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,data);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vh.pinner_type.setAdapter(typeAdapter);
        }

        // location的adapter
        if (vh.pinner_place.getAdapter()==null){
            saveMapRev(info.locations,locationMap);
            String[] data= map2StringArray(info.locations);
            ArrayAdapter typeAdapter=new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,data);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vh.pinner_place.setAdapter(typeAdapter);
        }

        // drink的adapter
        if (vh.pinner_drink.getAdapter()==null){
            saveMapRev(info.drinkings,drinkMap);
            String[] data= map2StringArray(info.drinkings);
            ArrayAdapter typeAdapter=new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,data);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vh.pinner_drink.setAdapter(typeAdapter);
        }


        // gears的adapter
        if (vh.pinner_huju.getAdapter()==null){
            saveMapRev(info.gears,gearsMap);
            String[] data= map2StringArray(info.gears);
            ArrayAdapter typeAdapter=new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,data);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vh.pinner_huju.setAdapter(typeAdapter);
        }

    }


    private String[] map2StringArray(Map<String,String> map){
        if (map==null||map.size()==0)return null;
        String result = "";

        Iterator iterator=map.entrySet().iterator();
        int index=0;
        while (iterator.hasNext()){
            Map.Entry entry= (Map.Entry) iterator.next();
            if (entry!=null){
                result+= String.valueOf(entry.getValue())+"/split/";
            }
            index++;
        }
        if (result!=null)
        return result.split("/split/");
        else return null;
    }

    /**
     * 因为服务器下发的字段并非按顺序递增，而是随机，因此这里需要将key和value反转
     * value作为key
     * key作为value
     * 通过pinner选择的时候，可以通过value得到key
     *
     * @param srcMap
     * @param dstMap
     */
    private void saveMapRev(Map<String,String> srcMap,Map<String,Integer> dstMap){
        if (srcMap==null||srcMap.size()==0)return;
        int index=0;
        Iterator iterator=srcMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry= (Map.Entry) iterator.next();
            if (entry!=null){
                int key=Integer.parseInt((String) entry.getKey());
                String value= (String) entry.getValue();
                dstMap.put(value,key);
            }
            index++;
        }
    }



    @Override
    public String getTitle() {
        return "创建任务";
    }


    private void submit() {
        //个数
        int preset_count=Integer.parseInt(vh.pinner_target.getSelectedItem().toString().trim());
        //组数
        int groupCount=Integer.parseInt(vh.pinner_group.getSelectedItem().toString().trim());
        //休息时间
        int breakTime=Integer.parseInt(vh.pinner_time.getSelectedItem().toString().trim());
        //运动类型
        int type=typeMap.get(vh.pinner_type.getSelectedItem().toString().trim());
        //开始时间
        long startTime=TimeUtils.string2Time(vh.pinner_start_time.getSelectedItem().toString().trim());
        //地点
        int place=locationMap.get(vh.pinner_place.getSelectedItem().toString().trim());
        //饮料
        int drinkType=drinkMap.get(vh.pinner_drink.getSelectedItem().toString().trim());
        //护具
        int gear=gearsMap.get(vh.pinner_huju.getSelectedItem().toString().trim());

        mCreateRequest.preset_count=preset_count;
        mCreateRequest.preset_group=groupCount;
        mCreateRequest.per_breaktime=breakTime;
        mCreateRequest.type=type;
        mCreateRequest.begin_at=startTime;
        mCreateRequest.location=place;
        mCreateRequest.drinking=drinkType;
        mCreateRequest.gear=gear;
        mCreateRequest.post(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create:
                isStart=false;
                submit();
                break;
            case R.id.start:
                isStart=true;
                submit();
                break;
            default:
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==150){
            EventBus.getDefault().post(new Events.ChangeToFragment(Events.ChangeToFragment.FRAG_INDEX));
        }
    }

    static class ViewHolder {
        public View rootView;
        public EditText mission_title;
        public Spinner pinner_target;
        public Spinner pinner_group;
        public Spinner pinner_time;
        public Spinner pinner_type;
        public Spinner pinner_start_time;
        public Spinner pinner_place;
        public Spinner pinner_drink;
        public Spinner pinner_huju;
        public Button create;
        public Button start;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mission_title = (EditText) rootView.findViewById(R.id.mission_title);
            this.pinner_target = (Spinner) rootView.findViewById(R.id.pinner_target);
            this.pinner_group = (Spinner) rootView.findViewById(R.id.pinner_group);
            this.pinner_time = (Spinner) rootView.findViewById(R.id.pinner_time);
            this.pinner_type = (Spinner) rootView.findViewById(R.id.pinner_type);
            this.pinner_start_time = (Spinner) rootView.findViewById(R.id.pinner_start_time);
            this.pinner_place = (Spinner) rootView.findViewById(R.id.pinner_place);
            this.pinner_drink = (Spinner) rootView.findViewById(R.id.pinner_drink);
            this.pinner_huju = (Spinner) rootView.findViewById(R.id.pinner_huju);
            this.create = (Button) rootView.findViewById(R.id.create);
            this.start = (Button) rootView.findViewById(R.id.start);
        }
    }


}
