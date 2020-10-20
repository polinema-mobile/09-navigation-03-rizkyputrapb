package id.ac.polinema.skor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;
import id.ac.polinema.skor.R;
import id.ac.polinema.skor.databinding.FragmentScoreBinding;
import id.ac.polinema.skor.models.GoalScorer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment {

    public static final String HOME_REQUEST_KEY = "home";
    public static final String AWAY_REQUEST_KEY = "away";
    public static final String SCORER_KEY = "scorer";
	FragmentScoreBinding binding;

    private List<GoalScorer> homeGoalScorerList;
    private List<GoalScorer> awayGoalScorerList;

    public ScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.awayGoalScorerList = new ArrayList<>();
        this.homeGoalScorerList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_score, container, false);
        binding.setHomeGoalScorerList(homeGoalScorerList);
        binding.setAwayGoalScorerList(awayGoalScorerList);
        binding.setFragment(this);
        getParentFragmentManager().setFragmentResultListener(HOME_REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                GoalScorer goalScorer = result.getParcelable(SCORER_KEY);
                homeGoalScorerList.add(goalScorer);
                binding.textHomeScorer.setText(getScorer(homeGoalScorerList, HOME_REQUEST_KEY));
            }
        });

        getParentFragmentManager().setFragmentResultListener(AWAY_REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                GoalScorer goalScorer = result.getParcelable(SCORER_KEY);
                awayGoalScorerList.add(goalScorer);
				binding.textAwayScorer.setText(getScorer(awayGoalScorerList, AWAY_REQUEST_KEY));
            }
        });
        return binding.getRoot();
    }

    public void onAddHomeClick(View view) {
        ScoreFragmentDirections.GoalScorerAction action = ScoreFragmentDirections.goalScorerAction(HOME_REQUEST_KEY);
        Navigation.findNavController(view).navigate(action);
    }

    public void onAddAwayClick(View view) {
        ScoreFragmentDirections.GoalScorerAction action = ScoreFragmentDirections.goalScorerAction(AWAY_REQUEST_KEY);
        Navigation.findNavController(view).navigate(action);
    }

    public String getScorer(List list, String key) {
        String str = "";
        Iterator iter = null;
        if (key.equals(HOME_REQUEST_KEY)) {
            iter = homeGoalScorerList.iterator();
        } else if (key.equals(AWAY_REQUEST_KEY)) {
            iter = awayGoalScorerList.iterator();
        }
        while (iter.hasNext()) {
            GoalScorer scorer = (GoalScorer) iter.next();
            str += scorer.getName() + " " + scorer.getMinute() + "\" ";
        }
        return str;
    }

}