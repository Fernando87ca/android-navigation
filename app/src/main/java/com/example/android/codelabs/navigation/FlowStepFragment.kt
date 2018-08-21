package com.example.android.codelabs.navigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

/**
 * Presents how multiple steps flow could be implemented.
 */
class FlowStepFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    setHasOptionsMenu(true)

    //val step = arguments?.getInt("step")

    val step = arguments?.let {
      val safeArgs = FlowStepFragmentArgs.fromBundle(it)
      safeArgs.step
    }

    return when (step) {
      2 -> inflater.inflate(R.layout.flow_step_two_fragment, container, false)
      else -> inflater.inflate(R.layout.flow_step_one_fragment, container, false)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<View>(R.id.next_button).setOnClickListener(
      Navigation.createNavigateOnClickListener(R.id.next_action)
    )
  }
}
