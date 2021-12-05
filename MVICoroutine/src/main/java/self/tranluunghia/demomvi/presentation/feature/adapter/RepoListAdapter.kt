package self.tranluunghia.demomvi.presentation.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import self.tranluunghia.demomvi.R
import self.tranluunghia.demomvi.databinding.ItemRepoBinding
import self.tranluunghia.demomvi.domain.model.GithubRepo

class RepoListAdapter(private val repoList: ArrayList<GithubRepo>) :
    RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemRepoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_repo,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindRepo(repoList[position])
    }

    override fun getItemCount(): Int = repoList.size

    fun updateItems(items: List<GithubRepo>) {
        repoList.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindRepo(repo: GithubRepo) {
            binding.textViewName.text = repo.fullName.orEmpty()
            binding.textViewUserName.text = repo.owner?.login.orEmpty()
            binding.textViewDescription.text = repo.description.orEmpty()
        }
    }
}