import React from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';

import Grid from '@mui/material/Grid';
import BuildStateData, {
  BuildState,
} from 'Components/MDClass/BuildStateData/BuildStateData';
import { useHistory } from 'react-router-dom';

interface BuildStateProps {
  buildState: BuildState;
}

export default function BuildStateBox({ buildState }: BuildStateProps) {
  const { buildStateId, state } = buildState;
  console.log(buildStateId);
  const history = useHistory();

  const linkPull: any = () => {
    history.push({
      pathname: '/builddetail',
      state: {
        id: buildStateId,
        name: 'Pull',
        state: state.pull,
      },
    });
  };

  const linkBuild: any = () => {
    history.push({
      pathname: '/builddetail',
      state: {
        buildStateId,
        buildType: 'Build',
      },
    });
  };

  const linkRun: any = () => {
    history.push({
      pathname: '/builddetail',
      state: {
        buildStateId,
        buildType: 'Run',
      },
    });
  };
  return (
    <Box my={3}>
      <Paper sx={{ backgroundColor: 'rgb(240, 240,240)', padding: 1 }}>
        <Grid container justifyContent="center" alignItems="center">
          <Grid item xs={4}>
            <Paper
              sx={{
                position: 'absolute',
                width: '5%',
                borderRadius: 10,
                background: 'linear-gradient(195deg, #42424a, #191919)',
                color: 'white',
              }}
            >
              <Typography align="center">#{buildState.buildStateId}</Typography>
            </Paper>
            <Paper
              elevation={3}
              sx={{ margin: 1, width: '50%', borderRadius: 3, padding: 3 }}
            >
              <Typography>{buildState.registDate}</Typography>
            </Paper>
          </Grid>
          <Grid item xs={8}>
            <Grid container spacing={1}>
              <Grid item xs={4} onClick={linkPull}>
                <Paper sx={{ padding: 5, borderRadius: 3 }}>
                  <Typography align="center">
                    {buildState.state.pull}
                  </Typography>
                </Paper>
              </Grid>
              <Grid item xs={4} onClick={linkBuild}>
                <Paper sx={{ padding: 5, borderRadius: 3 }}>
                  <Typography align="center">
                    {buildState.state.build}
                  </Typography>
                </Paper>
              </Grid>
              <Grid item xs={4} onClick={linkRun}>
                <Paper sx={{ padding: 5, borderRadius: 3 }}>
                  <Typography align="center">{buildState.state.run}</Typography>
                </Paper>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Paper>
    </Box>
  );
}
