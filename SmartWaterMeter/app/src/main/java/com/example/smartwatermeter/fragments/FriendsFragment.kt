package com.example.smartwatermeter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smartwatermeter.R
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.adapters.FriendsAdapter
import com.example.smartwatermeter.databinding.FragmentFriendsBinding
import com.example.smartwatermeter.models.FriendModel
import com.example.smartwatermeter.viewmodels.GameViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class FriendsFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentFriendsBinding
    private var flist: ArrayList<FriendModel> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_friends,
            container, false
        )

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        viewModel.getFriends()
        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                println(position.toString())
                sortList(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }





       viewModel.friends.observe(viewLifecycleOwner, Observer { friends ->
            viewModel.getMonth()

           var indexes = ArrayList<Int>()
           var names = ArrayList<String>()
           var mUsages = ArrayList<Long>()
            viewModel.musages.observe(viewLifecycleOwner, Observer { musages ->
                for (friend in musages) {
                    mUsages.add(friend.usage)
                    indexes.add(friends.indexOf(friend.appId))
                    names.add(friend.nameSurname)
                }
                viewModel.getDay()


                var dUsages = ArrayList<Long>()
                viewModel.dusages.observe(viewLifecycleOwner, Observer { dusages ->

                    for (friend in dusages)
                        dUsages.add(friend.usage)



                    var afriends = arrayListOf<String>()
                    afriends.add(MainActivity.StaticData.loginData!!.appId)
                    for(friend in friends) {
                        if(friend!=null)
                            afriends.add(friend)
                    }
                   // println(afriends.toString())
                    for ((i, usage) in dUsages.withIndex()) {
                            var nFriend = FriendModel(
                                afriends[i],
                                mUsages[i],
                                usage,
                                names[i],
                                indexes[i]
                            )
                            flist.add(nFriend)

                    }
                    flist.add(FriendModel("x",100344,38,"xx97",5))
                    sortList(0)
                    /*val fAdapter = FriendsAdapter(flist, this)
                    recView.adapter = fAdapter
                    recView.setHasFixedSize(true)
                    //println(flist.toString())*/

                })
            })
        })



        return binding.root
    }

    fun deleteFriend(friendId: String) {
        viewModel.deleteFriend(friendId)
    }

    private fun sortList(sortNum: Int) {
        when (sortNum) {
            0 -> flist.sortBy {it.index}
            1 -> flist.sortBy {it.dusage}
            else -> flist.sortBy {it.musage}
        }

        println(flist.toString())
        val fAdapter = FriendsAdapter(flist, this)
        binding.friendsList.adapter = fAdapter
        binding.friendsList.setHasFixedSize(true)

    }


}