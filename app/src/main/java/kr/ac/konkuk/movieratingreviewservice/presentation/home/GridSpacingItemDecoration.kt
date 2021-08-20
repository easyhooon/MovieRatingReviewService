package kr.ac.konkuk.movieratingreviewservice.presentation.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, //여백 설정
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapterPosition = parent.getChildAdapterPosition(view)
        val gridLayoutManager = parent.layoutManager as GridLayoutManager
        val spanSize = gridLayoutManager.spanSizeLookup.getSpanSize(adapterPosition)

        if (spanSize == spanCount) {
            outRect.left = spacing
            outRect.right = spacing
            outRect.top = spacing
            outRect.bottom = spacing
            return
        }

        //일정한 규칙이 없기 때문에 adapterPosition 으로 위치를 특정할 수 없음
        //spanIndex 를 가져와서 가장 왼쪽에 위치한 (시작) 타일들을 가져옴
        //spanCont -1 -> 오른쪽
        val column = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
        val itemHorizontalSpacing = ((spanCount + 1) * spacing) / spanCount.toFloat()
        when (column) {
            //왼쪽
            0 -> {
                outRect.left = spacing
                outRect.right = (itemHorizontalSpacing - spacing).toInt()
            }
            //오른쪽
            (spanCount - 1) -> {
                outRect.left = (itemHorizontalSpacing - spacing).toInt()
                outRect.right = spacing
            }
            //중간
            else -> {
                outRect.left = (itemHorizontalSpacing / 2).toInt()
                outRect.right = (itemHorizontalSpacing / 2).toInt()
            }
        }
        outRect.top = spacing
        outRect.bottom = spacing
    }
}