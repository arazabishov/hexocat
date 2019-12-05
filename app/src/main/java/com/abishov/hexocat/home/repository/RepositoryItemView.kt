package com.abishov.hexocat.home.repository

import android.content.Context
import androidx.core.content.ContextCompat
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.abishov.hexocat.R
import com.abishov.hexocat.common.picasso.PicassoServiceLocator
import com.abishov.hexocat.common.views.truss
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

class RepositoryItemView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

  @BindView(R.id.imageview_owner_logo)
  internal lateinit var imageViewLogo: ImageView

  @BindView(R.id.textview_repository_name)
  internal lateinit var textViewRepositoryName: TextView

  @BindView(R.id.textview_repository_description)
  internal lateinit var textViewRepositoryDescription: TextView

  @BindView(R.id.textview_repository_forks)
  internal lateinit var textViewForks: TextView

  @BindView(R.id.textview_repository_stars)
  internal lateinit var textViewStars: TextView

  private val descriptionColor: Int
  private lateinit var picasso: Picasso
  private lateinit var transformation: Transformation

  init {
    val outValue = TypedValue()

    context.theme.resolveAttribute(android.R.attr.textColorSecondary, outValue, true)

    descriptionColor = ContextCompat.getColor(
      context, outValue.resourceId
    )

    if (!isInEditMode) {
      picasso = PicassoServiceLocator.obtain(context)
      transformation = PicassoServiceLocator.obtainTransformation(context)
    }
  }

  override fun onFinishInflate() {
    super.onFinishInflate()
    ButterKnife.bind(this)
  }

  fun bindTo(repository: RepositoryViewModel) {
    picasso.load(repository.avatarUrl)
      .placeholder(R.drawable.avatar)
      .fit()
      .transform(transformation)
      .into(imageViewLogo)

    textViewRepositoryName.text = repository.name
    textViewForks.text = repository.forks
    textViewStars.text = repository.stars

    val description = truss {
      append(repository.login)
    }

    if (!repository.description.isBlank()) {
      description.span(ForegroundColorSpan(descriptionColor)) {
        append(" — ")
        append(repository.description)
      }
    }

    textViewRepositoryDescription.text = description.build()
  }
}