package com.lgcampos.carros.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lgcampos.carros.CarrosApplication;
import com.lgcampos.carros.R;
import com.lgcampos.carros.activity.CarroActivity;
import com.lgcampos.carros.adapter.CarroAdapter;
import com.lgcampos.carros.domain.Carro;
import com.lgcampos.carros.domain.CarroService;
import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import livroandroid.lib.utils.AndroidUtils;

public class CarrosFragment extends BaseFragment {

    private int tipo;
    private RecyclerView recyclerView;
    private List<Carro> carros;
    private SwipeRefreshLayout swipeLayout;
    private ActionMode actionMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.tipo = getArguments().getInt("tipo");
        }

        CarrosApplication.getInstance().getBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carros, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        return view;
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AndroidUtils.isNetworkAvailable(getContext())) {
                    taskCarros(true);
                } else {
                    swipeLayout.setRefreshing(false);
                    snack(recyclerView, R.string.msg_error_conexao_indisponivel);
                }
            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            taskCarros(false);
    }

    private void taskCarros(boolean pullToRefresh) {
        startTask("carros", new GetCarrosTask(pullToRefresh), pullToRefresh ? R.id.swipe_to_refresh : R.id.progress);
    }

    private CarroAdapter.CarroOnClickListener onClickCarro() {
        return new CarroAdapter.CarroOnClickListener() {
            public void onClickCarro(View view, int index) {
                Carro carro = carros.get(index);

                if (actionMode == null) {
                    Intent intent = new Intent(getActivity(), CarroActivity.class);
                    intent.putExtra("carro", Parcels.wrap(carro));
                    startActivity(intent);
                } else {
                    carro.selected = !carro.selected;
                    updateActionModeTitle();
                    recyclerView.getAdapter().notifyDataSetChanged();
                }

            }

            @Override
            public void onLongClickCarro(View itemView, int position) {
                if (actionMode != null) { return; }

                actionMode = getAppCompatActivity().startSupportActionMode(getActionModeCallback());
                Carro carro = carros.get(position);
                carro.selected = true;
                recyclerView.getAdapter().notifyDataSetChanged();
                updateActionModeTitle();
                toast("Clicou e segurou: " + carro.nome);
            }
        };
    }

    private ActionMode.Callback getActionModeCallback() {
        return new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_frag_carros_cab, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                List<Carro> selectedCarros = getSelectedCarros();
                if (item.getItemId() == R.id.action_remove) {
                    toast("Remover " + selectedCarros);
                } else if (item.getItemId() == R.id.action_share) {
                    toast("Compartilhar: " + selectedCarros);
                }

                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;

                for (Carro carro : carros) {
                    carro.selected = false;
                }

                recyclerView.getAdapter().notifyDataSetChanged();
            }
        };
    }

    private void updateActionModeTitle() {
        if (actionMode != null) {
            actionMode.setTitle("Selecione os carros.");
            actionMode.setSubtitle(null);
            List<Carro> selectedCarros = getSelectedCarros();
            if (selectedCarros.size() == 1) {
                actionMode.setSubtitle("1 carro selecionado");
            } else if (selectedCarros.size() > 1) {
                actionMode.setSubtitle(selectedCarros.size() + " carros selecionados");
            }
        }
    }

    public static CarrosFragment newInstance(int tipo) {
        Bundle args = new Bundle();
        args.putInt("tipo", tipo);
        CarrosFragment fragment = new CarrosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public List<Carro> getSelectedCarros() {
        List<Carro> list = new ArrayList<>();

        for (Carro carro : carros) {
            if (carro.selected) { list.add(carro); }
        }

        return list;
    }

    private class GetCarrosTask implements TaskListener<List<Carro>> {

        private final boolean pullToRefresh;

        public GetCarrosTask(boolean pullToRefresh) {

            this.pullToRefresh = pullToRefresh;
        }

        @Override
        public List<Carro> execute() throws Exception {
            return CarroService.getCarros(getContext(), tipo, pullToRefresh);
        }

        @Override
        public void updateView(List<Carro> carros) {

            if (carros != null) {
                CarrosFragment.this.carros = carros;
                recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
            }
        }

        @Override
        public void onError(Exception exception) {
            alert("Ocorreu algum erro ao buscar os dados.");
        }

        @Override
        public void onCancelled(String cod) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CarrosApplication.getInstance().getBus().unregister(this);
    }

    @Subscribe
    public void onBusAtualizarListaCarro(String refresh) {
        taskCarros(false);
    }
}
