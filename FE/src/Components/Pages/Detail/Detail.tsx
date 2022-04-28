import React, { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import CircularProgressWithLabel from 'Components/UI/Atoms/CircularProgressWithLabel/CircularProgressWithLabel';
import Grid from '@mui/material/Grid';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import BuildStateData, {
  BuildState,
} from 'Components/MDClass/BuildStateData/BuildStateData';
import { v4 as uuid } from 'uuid';
import BuildStateBox from './BuildStateBox/BuildStateBox';

export default function Detail() {
  const params = useParams();
  const [buildStates, setBuildStates] = useState<BuildState[]>([]);
  const [progress, setProgress] = useState('진행중... (미완성)');
  const [pullColor, setPullColor] = useState(100);
  const [runColor, setRunColor] = useState(200);
  const [buildColor, setBuildColor] = useState(500);

  useEffect(() => {
    axios
      .get('/api/project/build/total', { params })
      .then((res) => {
        setBuildStates([...res.data]);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <Box
      sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
    >
      <Box sx={{ width: '90%', padding: 3 }}>
        <Typography
          sx={{ textWeight: 4 }}
          my={4}
          variant="h2"
          textAlign="center"
        >
          Stage View
        </Typography>
        <Grid container spacing={2}>
          <Grid item xs={4}>
            <CircularProgressWithLabel
              value={progress}
              propsColor={pullColor}
            />
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
            <CircularProgressWithLabel
              value={progress}
              propsColor={buildColor}
            />
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
            <CircularProgressWithLabel value={progress} propsColor={runColor} />
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
          {buildStates.map((value) => {
            return <BuildStateBox key={uuid()} buildState={value} />;
          })}
        </Box>
      </Box>
    </Box>
  );
}
