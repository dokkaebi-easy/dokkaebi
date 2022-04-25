import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import AddIcon from '@mui/icons-material/Add';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';

import BuildBasic from 'Components/Pages/Setting/BuildPage/BuildBasic/BuildBasic';
import BuildData from 'MDClass/BuildData/BuildData';
import { v4 as uuid } from 'uuid';
import Paper from '@mui/material/Paper';

export default function BuildPage() {
  const [buildData, setBuildData] = useState<BuildData[]>([]);
  const handleClick = () => {
    const data = new BuildData();
    setBuildData((cur) => [...cur, data]);
  };

  const handlePropsClick = (index: number) => {
    setBuildData((cur) => {
      cur.splice(index, 1);
      return [...cur];
    });
  };
  useEffect(() => {
    const data = new BuildData();
    setBuildData([data]);
  }, []);

  return (
    <Box>
      <Box position="relative" sx={{ top: 20, left: 10 }}>
        <Paper sx={{ padding: 1, textAlign: 'center', width: 200 }}>
          <Typography variant="h5">Build Setting</Typography>
        </Paper>
      </Box>
      <Box>
        <Paper sx={{ padding: 3, pt: 4 }}>
          {buildData.map((value, index) => (
            <BuildBasic key={uuid()} index={index} Click={handlePropsClick} />
          ))}
          <Box sx={{ display: 'flex', justifyContent: 'end' }}>
            <Button
              onClick={handleClick}
              variant="outlined"
              startIcon={<AddIcon />}
            >
              Add
            </Button>
          </Box>
        </Paper>
      </Box>
    </Box>
  );
}
