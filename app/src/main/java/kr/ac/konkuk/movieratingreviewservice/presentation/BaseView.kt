package kr.ac.konkuk.movieratingreviewservice.presentation

interface BaseView<PresenterT : BasePresenter> {

    val presenter: PresenterT
}