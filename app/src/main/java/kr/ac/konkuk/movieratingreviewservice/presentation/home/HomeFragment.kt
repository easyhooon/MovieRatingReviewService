package kr.ac.konkuk.movieratingreviewservice.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.konkuk.movieratingreviewservice.databinding.FragmentHomeBinding
import kr.ac.konkuk.movieratingreviewservice.domain.model.FeaturedMovie
import kr.ac.konkuk.movieratingreviewservice.domain.model.Movie
import kr.ac.konkuk.movieratingreviewservice.extension.dip
import kr.ac.konkuk.movieratingreviewservice.extension.toGone
import kr.ac.konkuk.movieratingreviewservice.extension.toVisible
import kr.ac.konkuk.movieratingreviewservice.presentation.home.HomeAdapter.Companion.ITEM_VIEW_TYPE_FEATURED
import kr.ac.konkuk.movieratingreviewservice.presentation.home.HomeAdapter.Companion.ITEM_VIEW_TYPE_SECTION_HEADER
import org.koin.android.scope.ScopeFragment

class HomeFragment : ScopeFragment(), HomeContract.View {

    private var binding: FragmentHomeBinding? = null

    override val presenter: HomeContract.Presenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View = FragmentHomeBinding.inflate(inflater, container, false)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindView()
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showLoadingIndicator() {
        binding?.progressBar?.toVisible()
    }

    override fun hideLoadingIndicator() {
        binding?.progressBar?.toGone()
    }

    override fun showErrorDescription(message: String) {
        binding?.recyclerView?.toGone()
        binding?.errorDescriptionTextView?.toVisible()
        binding?.errorDescriptionTextView?.text = message
    }

    override fun showMovies(featuredMovie: FeaturedMovie?, movies: List<Movie>) {
        binding?.recyclerView?.toVisible()
        binding?.errorDescriptionTextView?.toGone()
        (binding?.recyclerView?.adapter as? HomeAdapter)?.run {
            addData(featuredMovie, movies)
            notifyDataSetChanged()
        }
    }

    private fun initViews() {
        binding?.recyclerView?.apply {
            adapter = HomeAdapter()
            val gridLayoutManager = createGridLayoutManager()
            layoutManager = gridLayoutManager
            //grid 에서 decoration 함수 간격 조정
            addItemDecoration(GridSpacingItemDecoration(gridLayoutManager.spanCount, dip(6f)))
        }
    }

    private fun bindView() {
        (binding?.recyclerView?.adapter as? HomeAdapter)?.apply {
            onMovieClickListener = { movie ->
                val action = HomeFragmentDirections.toMovieReviewsAction(movie)
                findNavController().navigate(action)
            }
        }
    }


    private fun RecyclerView.createGridLayoutManager(): GridLayoutManager =
        //default spanCount 3으로 설정
        GridLayoutManager(context, 3, RecyclerView.VERTICAL, false).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                //열이 달라지는 (3열 -> 1열) 것을 처리하기 위한 로직 설정
                override fun getSpanSize(position: Int): Int =
                    when (adapter?.getItemViewType(position)) {
                        ITEM_VIEW_TYPE_SECTION_HEADER,
                        ITEM_VIEW_TYPE_FEATURED -> {
                            spanCount
                        }
                        //movie 영역은 1칸씩 사용
                        else -> {
                            1
                        }
                    }
            }
        }

}