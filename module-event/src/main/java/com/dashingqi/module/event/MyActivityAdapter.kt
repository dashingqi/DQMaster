package com.dashingqi.module.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dashingqi.module.event.databinding.MyAdapterItemBinding

/**
 *
 * @ProjectName: DQMaster
 * @Package: com.dashingqi.module.event
 * @ClassName: MyActivityAdapter
 * @Author: DashingQI
 * @CreateDate: 2020/12/3 10:35 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/3 10:35 PM
 * @UpdateRemark:
 * @Version: 1.0
 */

class MyActivityAdapter(var data:ArrayList<String> = ArrayList()):RecyclerView.Adapter<MyActivityAdapter.MyViewHolder>() {


    class MyViewHolder(var itemRootView: MyAdapterItemBinding):RecyclerView.ViewHolder(itemRootView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val itemBinding = MyAdapterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val itemBinding =  holder.itemRootView as MyAdapterItemBinding
        itemBinding.tvTag.text = data[position]
        itemBinding.executePendingBindings()
    }

}