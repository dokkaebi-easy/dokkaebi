import React from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';

import Grid from '@mui/material/Grid';
import BuildStateData, {
  BuildState,
} from 'Components/MDClass/BuildStateData/BuildStateData';

interface BuildStateProps {
  buildState: BuildState;
}

export default function BuildStateBox({ buildState }: BuildStateProps) {
  return (
    <Box my={3}>
      <Paper sx={{ backgroundColor: 'rgb(240, 240,240)', padding: 3 }}>
        <Grid container>
          <Grid item xs={1}>
            <Typography>#{buildState.buildStateId}</Typography>
          </Grid>
          <Grid item xs={3}>
            <Typography>{buildState.registDate}</Typography>
          </Grid>
          <Grid item xs={8}>
            <Grid container>
              <Grid item xs={4}>
                <Typography align="center">{buildState.state.pull}</Typography>
              </Grid>
              <Grid item xs={4}>
                <Typography align="center">{buildState.state.build}</Typography>
              </Grid>
              <Grid item xs={4}>
                <Typography align="center">{buildState.state.run}</Typography>
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Paper>
    </Box>
  );
}
