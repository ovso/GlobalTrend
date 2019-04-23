package io.github.ovso.globaltrend.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.adapter.MainAdapter.MainViewHolder

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MainViewHolder {
    TODO("not implemented")
  }

  override fun getItemCount(): Int {
    TODO("not implemented")
  }

  override fun onBindViewHolder(
    holder: MainViewHolder,
    position: Int
  ) {
    TODO("not implemented")
  }

  class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private companion object {
      fun create(parent: ViewGroup): MainViewHolder {
        var inflate = LayoutInflater.from(parent.context)
          .inflate(R.layout.fragment_blank, parent, false)
        return MainViewHolder(inflate)
      }
    }
  }
}