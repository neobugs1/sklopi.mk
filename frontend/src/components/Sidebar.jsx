import React, { useState, useEffect } from "react";
import { Box, VStack, Heading, Checkbox, Text, RangeSlider, RangeSliderTrack, RangeSliderFilledTrack, RangeSliderThumb, HStack } from "@chakra-ui/react";

const Sidebar = ({ filters, setFilters, distinctFilters, filterConfig }) => {
  const [priceRange, setPriceRange] = useState([distinctFilters.minPrice, distinctFilters.maxPrice]);

  useEffect(() => {
    setPriceRange([distinctFilters.minPrice, distinctFilters.maxPrice]);
  }, [distinctFilters.minPrice, distinctFilters.maxPrice]);

  const handleCheckboxChange = (e) => {
    const { name, value, checked } = e.target;
    setFilters((prevFilters) => ({
      ...prevFilters,
      [name]: checked ? [...(prevFilters[name] || []), value] : (prevFilters[name] || []).filter((item) => item !== value),
    }));
  };

  const handlePriceChange = (value) => {
    setPriceRange(value);
    setFilters((prevFilters) => ({
      ...prevFilters,
      priceRange: value,
    }));
  };

  return (
    <Box width="250px" marginRight="20px" bg="var(--filter-bg)" borderRadius="8px" p="15px">
      <Heading as="h3" color="var(--secondary-color)" size="md" mb="10px">
        Filters
      </Heading>
      <VStack spacing={4}>
        {filterConfig.map(({ key, label }) => (
          <Box key={key}>
            <Heading as="h4" size="sm">
              {label}
            </Heading>
            {distinctFilters[`distinct${key[0].toUpperCase() + key.slice(1)}`]?.map(
              (value) => (
                console.log(`distinct${key[0].toUpperCase() + key.slice(1)}`),
                (
                  <Checkbox key={value} name={key} value={value} onChange={handleCheckboxChange}>
                    {value}
                  </Checkbox>
                )
              )
            )}
          </Box>
        ))}
        <Box>
          <Text mb={2}>Price:</Text>
          <RangeSlider
            min={distinctFilters.minPrice}
            max={distinctFilters.maxPrice}
            step={10}
            value={priceRange}
            onChange={handlePriceChange}
            aria-label={["min-price", "max-price"]}
          >
            <RangeSliderTrack>
              <RangeSliderFilledTrack />
            </RangeSliderTrack>
            <RangeSliderThumb index={0} />
            <RangeSliderThumb index={1} />
          </RangeSlider>
          <HStack justify="space-between" mt={2}>
            <Text>Min: {priceRange[0]}</Text>
            <Text>Max: {priceRange[1]}</Text>
          </HStack>
        </Box>
      </VStack>
    </Box>
  );
};

export default Sidebar;
