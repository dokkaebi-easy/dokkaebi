import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import CircularProgressWithLabel from 'Components/UI/Atoms/CircularProgressWithLabel/CircularProgressWithLabel';
import Grid from '@mui/material/Grid';
import { api } from '../../../api/index';

export default function Detail() {
  const [progress, setProgress] = useState(10);
  const [pullColor, setPullColor] = useState(100);
  const [runColor, setRunColor] = useState(200);
  const [buildColor, setBuildColor] = useState(500);

  return (
    <Box
      mt={3}
      sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
    >
      <Box sx={{ width: '90%', padding: 3 }}>
        <Grid container spacing={2}>
          <Grid item xs={4}>
            <CircularProgressWithLabel
              value={progress}
              propsColor={pullColor}
            />
            <Typography align="center" variant="h3">
              Git Pull
            </Typography>
          </Grid>
          <Grid item xs={4}>
            <CircularProgressWithLabel
              value={progress}
              propsColor={buildColor}
            />
            <Typography align="center" variant="h3">
              Dokcer Build
            </Typography>
          </Grid>
          <Grid item xs={4}>
            <CircularProgressWithLabel value={progress} propsColor={runColor} />
            <Typography align="center" variant="h3">
              Dokcer Run
            </Typography>
          </Grid>
        </Grid>
      </Box>
    </Box>
  );
}
