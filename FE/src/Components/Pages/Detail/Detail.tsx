import { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import CircularProgressWithLabel from 'Components/UI/Atoms/CircularProgressWithLabel/CircularProgressWithLabel';
import Grid from '@mui/material/Grid';
import axios from 'axios';
import { useParams, useHistory, Link } from 'react-router-dom';
import {
  BuildState,
  State,
} from 'Components/MDClass/BuildStateData/BuildStateData';
import BuildStateBox from 'Components/Pages/Detail/BuildStateBox/BuildStateBox';
import { v4 as uuid } from 'uuid';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import { useRunStore } from 'Components/Store/RunStore/RunStore';

export default function Detail() {
  const run = useRunStore((state) => state.run);
  const setRun = useRunStore((state) => state.setRun);
  const intervals = useRunStore((state) => state.intervals);
  const setIntervals = useRunStore((state) => state.setIntervals);
  const [buildStates, setBuildStates] = useState<BuildState[]>([]);
  const [progress, setProgress] = useState(['Waiting', 'Waiting', 'Waiting']);
  const [loading, setLoading] = useState(false);

  const history = useHistory();
  const params = useParams();

  const handleBuildDelClick = () => {
    console.log(params);

    axios.delete(`/api/project/${params}`).then(() => {
      history.push(`/`);
    });
  };

  const handleBackClick = () => {
    history.push(`/`);
  };

  const handleBuildClick = () => {
    setLoading(true);
    setRun(1);

    axios
      .post('/api/project/build', null, { params })
      .then(() => {
        setLoading(false);
        setRun(0);
      })
      .catch(() => {
        setLoading(false);
        setRun(2);
      });
  };

  useEffect(() => {
    axios
      .get('/api/project/build/total', { params })
      .then((res) => {
        const data = res.data as BuildState[];
        data.reverse();
        setBuildStates(data);
        const stateData = data[0].buildTotalDetailDtos.map(
          (value) => value.stateType,
        );
        setProgress(stateData);
      })
      .catch();

    if (run === 1) {
      const interval = setInterval(() => {
        axios
          .get('/api/project/build/total', { params })
          .then((res) => {
            const data = res.data as BuildState[];
            data.reverse();
            setBuildStates([...data]);
            const stateData = data[0].buildTotalDetailDtos.map(
              (value) => value.stateType,
            );
            setProgress(stateData);
          })
          .catch();
      }, 1000);
      setIntervals([...intervals, interval]);
    }

    return () => {
      setBuildStates([]);
    };
  }, []);

  useEffect(() => {
    if (loading && run === 1) {
      const interval = setInterval(() => {
        axios
          .get('/api/project/build/total', { params })
          .then((res) => {
            const data = res.data as BuildState[];
            data.reverse();
            setBuildStates([...data]);
            const stateData = data[0].buildTotalDetailDtos.map(
              (value) => value.stateType,
            );
            setProgress(stateData);
          })
          .catch();
      }, 1000);
      setIntervals([...intervals, interval]);
    }
    if (run !== 1) {
      setTimeout(() => {
        intervals.map((value) => clearInterval(value));
      }, 2000);
    }
  }, [run]);

  return (
    <Box
      sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
    >
      <Box sx={{ width: '90%', padding: 3 }}>
        <Stack mt={5} spacing={2} direction="row" justifyContent="flex-end">
          <Button
            variant="contained"
            sx={{
              background: 'linear-gradient(195deg, #ee6666, #ff2222)',
              color: 'white',
            }}
            onClick={handleBuildDelClick}
          >
            Del
          </Button>

          <Button
            variant="contained"
            onClick={handleBuildClick}
            sx={{
              background: 'linear-gradient(195deg, #777, #191919)',
              color: 'white',
            }}
          >
            Build
          </Button>

          <Link
            to={`/setting/${Object.values(params)[0]}`}
            style={{ color: 'black', textDecoration: 'none' }}
          >
            <Button
              variant="contained"
              sx={{
                background: 'linear-gradient(195deg, #777, #191919)',
                color: 'white',
              }}
            >
              Edit
            </Button>
          </Link>

          <Button
            variant="contained"
            onClick={handleBackClick}
            sx={{
              background: 'linear-gradient(195deg, #777, #191919)',
              color: 'white',
            }}
          >
            Back
          </Button>
        </Stack>
        <Typography
          sx={{ textWeight: 4 }}
          my={4}
          variant="h2"
          textAlign="center"
        >
          Recent Stage View
        </Typography>
        <Grid container spacing={2}>
          <Grid item xs={4}>
            <CircularProgressWithLabel value={progress[0]} />
            <Typography
              sx={{ textWeight: 3 }}
              mt={2}
              align="center"
              variant="h4"
            >
              Git Pull
            </Typography>
          </Grid>
          <Grid item xs={4}>
            <CircularProgressWithLabel value={progress[1]} />
            <Typography
              sx={{ textWeight: 3 }}
              mt={2}
              align="center"
              variant="h4"
            >
              Build
            </Typography>
          </Grid>
          <Grid item xs={4}>
            <CircularProgressWithLabel value={progress[2]} />
            <Typography
              sx={{ textWeight: 3 }}
              mt={2}
              align="center"
              variant="h4"
            >
              Run
            </Typography>
          </Grid>
        </Grid>
        <Box mt={10}>
          <Box sx={{ padding: 3 }}>
            <Grid container alignItems="flex-end">
              <Grid item xs={4}>
                <Typography sx={{ textWeight: 3 }} variant="h3">
                  Sate List
                </Typography>
              </Grid>
              <Grid item xs={8}>
                <Grid container justifyContent="center" alignItems="flex-end">
                  <Grid item xs={4}>
                    <Typography align="center" variant="h5">
                      Pull
                    </Typography>
                  </Grid>
                  <Grid item xs={4}>
                    <Typography align="center" variant="h5">
                      Build
                    </Typography>
                  </Grid>
                  <Grid item xs={4}>
                    <Typography align="center" variant="h5">
                      Run
                    </Typography>
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </Box>
          {buildStates.length ? (
            <>
              {buildStates.map((value, index) => {
                return <BuildStateBox key={uuid()} buildState={value} />;
              })}
            </>
          ) : (
            <Paper sx={{ backgroundColor: 'rgb(240, 240,240)', padding: 3 }}>
              <Typography align="center" variant="h6">
                빌드 해주세요...
              </Typography>
            </Paper>
          )}
        </Box>
      </Box>
    </Box>
  );
}
