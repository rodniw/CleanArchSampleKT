package dev.rodni.ru.cleanarchsamplekt.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.rodni.ru.cleanarchsamplekt.domain.Document
import dev.rodni.ru.cleanarchsamplekt.R
import dev.rodni.ru.cleanarchsamplekt.framework.ReaderAppViewModelFactory
import dev.rodni.ru.cleanarchsamplekt.presentation.library.LibraryFragment
import dev.rodni.ru.cleanarchsamplekt.presentation.reader.BookmarksAdapter
import dev.rodni.ru.cleanarchsamplekt.presentation.reader.ReaderViewModel
import kotlinx.android.synthetic.main.fragment_reader.*

class ReaderFragment : Fragment() {

  companion object {

    fun newInstance(document: Document) = ReaderFragment().apply {
      arguments = ReaderViewModel.createArguments(document)
    }
  }

  private lateinit var viewModel: ReaderViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_reader, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    val adapter = BookmarksAdapter {
      viewModel.openBookmark(it)
    }
    bookmarksRecyclerView.adapter = adapter

    viewModel = ViewModelProviders.of(this, ReaderAppViewModelFactory)
        .get(ReaderViewModel::class.java)

    viewModel.document.observe(this, Observer {
      if (it == Document.EMPTY) {
        // Show file picker action.
        startActivityForResult(IntentUtil.createOpenIntent(), LibraryFragment.READ_REQUEST_CODE)
      }
    })

    viewModel.bookmarks.observe(this, Observer {
      adapter.update(it)
    })

    viewModel.isBookmarked.observe(this, Observer {
      val bookmarkDrawable = if (it) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
      tabBookmark.setCompoundDrawablesWithIntrinsicBounds(0, bookmarkDrawable, 0, 0)
    })

    viewModel.isInLibrary.observe(this, Observer {
      val libraryDrawable = if(it) R.drawable.ic_library else R.drawable.ic_library_border
      tabLibrary.setCompoundDrawablesRelativeWithIntrinsicBounds(0, libraryDrawable, 0, 0)
    })

    viewModel.currentPage.observe(this, Observer { showPage(it) })
    viewModel.hasNextPage.observe(this, Observer { tabNextPage.isEnabled = it })
    viewModel.hasPreviousPage.observe(this, Observer { tabPreviousPage.isEnabled = it })
    viewModel.loadArguments(arguments)

    tabBookmark.setOnClickListener { viewModel.toggleBookmark() }
    tabLibrary.setOnClickListener { viewModel.toggleInLibrary() }
    tabNextPage.setOnClickListener { viewModel.nextPage() }
    tabPreviousPage.setOnClickListener { viewModel.previousPage() }
  }

  private fun showPage(page: PdfRenderer.Page) {
    iv_page.visibility = View.VISIBLE
    pagesTextView.visibility = View.VISIBLE
    tabPreviousPage.visibility = View.VISIBLE
    tabNextPage.visibility = View.VISIBLE

    if (iv_page.drawable != null) {
      (iv_page.drawable as BitmapDrawable).bitmap.recycle()
    }

    val size = Point()
    activity?.windowManager?.defaultDisplay?.getSize(size)

    val pageWidth = size.x
    val pageHeight = page.height * pageWidth / page.width

    val bitmap = Bitmap.createBitmap(
        pageWidth,
        pageHeight,
        Bitmap.Config.ARGB_8888)

    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
    iv_page.setImageBitmap(bitmap)

    pagesTextView.text = getString(
        R.string.page_navigation_format,
        page.index,
        viewModel.renderer.value?.pageCount
    )

    page.close()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    // Process open file intent.
    if (requestCode == LibraryFragment.READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      data?.data?.also { uri -> viewModel.openDocument(uri) }
    } else {
      super.onActivityResult(requestCode, resultCode, data)
    }
  }

}
