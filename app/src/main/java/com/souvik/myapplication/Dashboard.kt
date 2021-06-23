package com.souvik.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.souvik.myapplication.Adapter.CategoryDetailsAdapter
import com.souvik.myapplication.Adapter.ProductDetailsAdapter
import com.souvik.myapplication.Model.Data
import com.souvik.myapplication.Model.Item
import com.souvik.myapplication.Model.Result
import com.souvik.myapplication.Utils.Status
import com.souvik.myapplication.Utils.Utility
import com.souvik.myapplication.databinding.FragmentDashboardBinding

class Dashboard : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: DashBoardViewModel
    private val TAG = Dashboard::class.java.simpleName
    private lateinit var adapter: ProductDetailsAdapter
    private lateinit var categoryAdapter: CategoryDetailsAdapter
    private val list = ArrayList<Item>()
    private val categoryList = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpObserver()
        initView()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(DashBoardViewModel::class.java)
        val data = HashMap<String, String>()
        data["store_id"] = "3"
        data["u_id"] = ""
        data["access_type"] = "1"
        data.put("source", "mob")
        if(Utility.isNetworkConnected(requireContext()))
            viewModel.getProductDetails(data)
        else
            Snackbar.make(binding.root,"No Internet!",Snackbar.LENGTH_LONG).show()
    }

    private fun setUpObserver() {
        viewModel.dataList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loadingLayout.visibility = View.GONE
                    Log.d(TAG, "setUpObserver: ${it.data}")
                    categoryList.clear()
                    it.data?.result?.data?.let { it1 ->
                        categoryList.addAll(it1)
                        //setUpTabView()
                        setUpCategoryAdapter()
                    }
                }
                Status.LOADING -> {
                    binding.loadingLayout.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Log.d(TAG, "setUpObserver: ${it.message}")
                    binding.loadingLayout.visibility = View.GONE
                }
            }
        })
    }

    private fun setUpTabView() {
        categoryList.forEach {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(it.cat_name))
        }

        //Initially Fixing to all
        list.clear()
        list.addAll(categoryList.filter { it.cat_name == "All" }[0].items)
        adapter.notifyDataSetChanged()

        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                list.clear()
                list.addAll(categoryList.filter { it.cat_name == tab?.text }[0].items)
                adapter.notifyDataSetChanged()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun setUpCategoryAdapter(){

        //Initially Fixing to all
        list.clear()
        list.addAll(categoryList.filter { it.cat_name == "All" }[0].items)
        adapter.notifyDataSetChanged()

        //fixing is selected by default
        categoryList.filter { it.cat_name == "All" }[0].isSelected =true

        categoryAdapter = CategoryDetailsAdapter(categoryList,object: CategoryDetailsAdapter.OnItemClickListener{
            override fun onCategorySelected(productList: List<Item>, position: Int) {
                list.clear()
                list.addAll(productList)
                categoryList.filter { it.isSelected }[0].isSelected =false
                categoryList[position].isSelected =true
                categoryAdapter.notifyDataSetChanged()
                adapter.notifyDataSetChanged()
            }
        })
        binding.rvShowCategory.adapter = categoryAdapter
        categoryAdapter.notifyDataSetChanged()
    }

    private fun initView() {
        adapter = ProductDetailsAdapter(list,object :ProductDetailsAdapter.OnItemRowClickListner{
            override fun onFavClick(position: Int) {

            }

            override fun onContainerClick(position: Int) {

            }

        })
        binding.rvShowElements.adapter = adapter
        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }
        binding.ivBellIcon.setOnClickListener {

        }
    }
}