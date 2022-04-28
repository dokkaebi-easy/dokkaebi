import React, { useEffect, useState } from 'react';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Skeleton from '@mui/material/Skeleton';
import CircularProgress from '@mui/material/CircularProgress';

interface ProgressProps {
  value: string;
  propsColor: number;
}

const styleBox = {
  top: 0,
  left: 0,
  bottom: 0,
  right: 0,
  margin: 'auto auto',
  position: 'absolute',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  backgroundColor: 'white',
  borderRadius: 100,
};

export default function CircularProgressWithLabel({
  value,
  propsColor,
}: ProgressProps) {
  const [color, setColor] = useState(propsColor);
  return (
    <Box
      sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
    >
      <Box sx={{ position: 'relative', display: 'inline-flex' }}>
        <Skeleton
          variant="circular"
          animation="wave"
          width={300}
          height={300}
          sx={{ opacity: '1' }}
        />
        <Box
          sx={{
            ...styleBox,
            opacity: '0.5',
            background: 'linear-gradient(45deg, blueviolet, aquamarine)',
            stroke: 1,
            strokeDasharray: 200,
          }}
          width={300}
          height={300}
        />

        <Box
          sx={{ ...styleBox, backgroundColor: 'rgb(240, 240,240)' }}
          width="80%"
          height="80%"
        >
          <Typography variant="caption" component="div" color="text.secondary">
            {value}
          </Typography>
        </Box>
      </Box>
    </Box>
  );
}
